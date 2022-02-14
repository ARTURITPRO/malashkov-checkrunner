package edu.clevertec.check.aspect;

import edu.clevertec.check.annotation.Caches;
import edu.clevertec.check.cache.Cache;
import edu.clevertec.check.dto.Entity;
import edu.clevertec.check.service.CacheService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

/**
 * <p>An aspect class that adds functionality for caching entities when receiving,
 * deleting, creating and updating in the dao.</p>
 *
 * @author Artur Malashkov
 * @see Caches
 * @see Cache
 * @see CacheService
 * @since 1.8
 */


/**@Aspect — модуль в котором собраны описания Pointcut и Advice.*/
@Aspect
public class CachAspect {
/*Создаём инстанс кэша через паттерн Singleton, (INSTANCE = new CacheService()),
  в конструкторе этого инстанса прописан алгоритм выбора LRU или LFU кэша
   */
    private final Cache cache = CacheService.getInstance().getCache();
/** @Pointcut - это срез, запрос точек присоединения, это может быть одна и более точек.
 *  @annotation (..) - указывает путь (..) к описанию аннотации, которую я хочу применить
 *
 *  @Pointcut ("execution(public * com.example.demoAspects.MyService.*(..))")
 *  public void callAtMyServicePublic() { }
 *  execution ()- указывает описание методов, к которым будет применяться метод "callAtMyServicePublic()" */

    @Pointcut("@annotation(edu.clevertec.check.annotation.Caches)")
    public void joinToCache() {
    }

    /** join point — это точки наблюдения, присоединения к коду,
     * где планируется введение функциональности.
     * Advice — набор инструкций выполняемых на точках среза (Pointcut).
     * Инструкции можно выполнять по событию разных типов.
     * @Around - запускает совет (Advice) до и после выполнения метода*/
    @SuppressWarnings("unchecked")
    @Around("joinToCache() && execution(public * find*(..))")
    public Object searchInTheCacheAndRepo(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("SearchInTheCacheAndDatabase");
        Class<?> clazz = joinPoint.getSignature().getDeclaringType();
        Class<?> genericClass = (Class<?>) ((ParameterizedType) clazz.getGenericInterfaces()[0]).getActualTypeArguments()[1];
        Object[] arguments = joinPoint.getArgs();

        Optional<Entity> maybeEntity = cache.find((Integer) arguments[0], genericClass);

        if (maybeEntity.isPresent()) {
            return maybeEntity;
        } else {
            Optional<Entity> entity = (Optional<Entity>) joinPoint.proceed(arguments);
            entity.ifPresent(cache::save);
            return entity;
        }
    }

    @Around("joinToCache() && execution(public * save*(..))")
    public Object putToCacheAndDatabase(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();

        Entity entity = (Entity) joinPoint.proceed(joinPoint.getArgs());

        cache.save(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Around("joinToCache() && execution(public * update*(..))")
    public Object refreshedInRepoAndCache(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();

        Optional<Entity> entity = (Optional<Entity>) joinPoint.proceed(arguments);

        entity.ifPresent(cache::update);
        return entity;
    }

    @Around("joinToCache() && execution(public * delete*(..))")
    public Object disposalInCacheAndRepo(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> clazz = joinPoint.getSignature().getDeclaringType();
        Class<?> genericClass = (Class<?>) ((ParameterizedType) clazz.getGenericInterfaces()[0]).getActualTypeArguments()[1];
        Object[] arguments = joinPoint.getArgs();

        boolean result = (boolean) joinPoint.proceed(arguments);

        cache.delete((Integer) arguments[0], genericClass);
        return result;
    }
}
