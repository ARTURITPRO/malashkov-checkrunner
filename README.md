
Запуск :
1.) mvn compile
2.) mvn exec:java -Dexec.mainClass=CheckRunner -Dexec.args="1-2 2-3 3-5 4-1 5-4 6-3 7-4 8-2 maestrocard-623587456214"
 	или
    mvn exec:java -Dexec.mainClass=CheckRunner -Dexec.args="src\main\resources\data.txt"

Запуск тестов : mvn test

* Если в качестве аргументов передан путь к data файлу, данные будут взяты из него
  и чек будет записан в созданный файл src/main/resources/receipt.txt
* Если в кач-ве аргументов сами данные (формата 1-3 и тд, где 1-id 3-кол-во), чек будет
  выведен в консоль

Передача товаров и их кол-во в аргументы
* Первая цифра id, в случае если переданный id превышает максимально допустимый (8)
  будет выброшено исключение NoSuchProductException
* Если передан аргумент с id который уже был передан в этом же списке аргументов, то
  их кол-во суммируется. Например "2-2 2-3 2-4" -> "2-9"

Передача карты в аргументы :
* Карта может быть двух типов mastercard (с 6% скидкой) или maestrocard (с 3% скидкой),
  в противном случае будет выброшено исключение InvalidCardTypeException
* Номер карты должен состоять из 12 цифр, в противном случае будет выброшено
  исключение InvalidCardNumberException
* При передаче карты в аргументы, тип карты и её номер разделяет дефис



** Пример результата с аргументами "1-2 2-3 3-5 4-1 5-4 6-3 7-4 8-2 maestrocard-623587456214"


****************************************
*          Storage "Dionis17"          *
*           EKE "Centrail"             *
*          +375(29)937-99-92           *
ZWDN:304219              CKHO:300030394
REGN:3100016076           UNP:390286042
KASSA:0001 Change:000750  DKH:000271821
31 Osipova Tatiana     CHK:01/000000285
TIME  23:22:03		   DATE  2021-11-27
----------------------------------------
QTY   DESCRIPTION       PRICE      TOTAL
4     Snickers          $3.5       $14
1     Fish Perch        $1.3       $1,3
*     The item KitKat is promotional
*     Its amount is more than 5
*     You get a 10% discount
*     The cost KitKat will be:$2,52
*     Instead of $2.8
5     KitKat            $2,52      $12,6
2     Fish Sturgeon     $1.8       $3,6
3     Bounty            $2.3       $6,9
4     Tic-tac           $1.7       $6,8
3     Meat              $5.01      $15,03
2     NUTS              $1.4       $2,8
----------------------------------------
#	  MAESTROCARD 623587
#	  has been provided
#	  Included 3% discount
----------------------------------------
####  Total cost:                 $61,14
