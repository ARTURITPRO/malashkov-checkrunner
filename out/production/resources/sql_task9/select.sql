-- Найти все товары по первой букве 'S' в названии товара со всеми характеристиками и ценой
SELECT name, price,  engine_power,   amperage,   efficiency_pump
FROM product
WHERE name LIKE 'S%';

-- Найти все товары по первой букве 'E' в названии товара с выводом страны производителя
SELECT p.name,  f.country AS country
FROM product p
         JOIN firm f ON p.producer_id = f.id
WHERE p.name LIKE 'E%';

-- Найти все товары с буквой 'S' в названии с выводом фирмы производителя
SELECT p.name, f.pump_brand AS brand
FROM product p
         JOIN firm f ON p.producer_id = f.id
WHERE p.name LIKE 'S%';

-- Вывести  информацию о предприятии "Gomelvodokanal" и стоимость  покупок погружных насосов предприятием
SELECT c.name, c.city, c.adress, concat_ws(' ', c.tel_code, c.tel_number) AS "phone number", p.count*pr.price AS cost
FROM company c
         JOIN purchases p ON c.id = p.company_id
         JOIN product pr ON c.id = pr.id
WHERE c.id = 1;

-- Вывести  сумму закупки погружных насосов каждым предприятием и отсортировать сумму по убыванию
SELECT c.name,  SUM(p.count*pr.price) AS cost
FROM company c
         JOIN purchases p ON c.id = p.company_id
         JOIN product pr ON c.id = pr.id
GROUP BY c.name
ORDER BY cost DESC;

-- Найти  все погружные насосы,купленные всеми предприятиями за всё время, вычислить сумму каждой покупки в отдельности,
-- отсортировать по убыванию суммы покупок,
SELECT c.name, pr.name, pu.count,pr.price, pu.count*pr.price AS cost
FROM purchases pu
         JOIN company c  ON c.id = pu.company_id
         JOIN product pr ON pr.id = pu.product_id
ORDER BY cost DESC;
--Найти мощные погружные насосы,купленные предприятиями, у которых ток превышает 70 Ампер,
-- а производительность подачи воды  больше  от, отссортировать данные ампеража по убыванию.
SELECT c.name, pr.name, pr.amperage, pr.efficiency_pump
FROM purchases pu
         JOIN company c  ON c.id = pu.company_id
         JOIN product pr ON pr.id = pu.product_id
WHERE pr.amperage  IN (
    SELECT product.amperage
    FROM product
    WHERE   amperage >= 70
) AND efficiency_pump BETWEEN 40 AND 70
ORDER BY pr.amperage DESC;

--Внести изменения в таблицу товаров,  т.к. в связи с кризисом насос марки 'SS6E8А' подорожал до 1000
UPDATE product
SET price = 1000
WHERE name = 'SS6E8А';

-- Показать изменения в таблице product
SELECT name, price
FROM product
WHERE name = 'SS6E8А';

--Снова внести изменения в таблицу товаров,  т.к. в связи с кризисом, насосы всех марок  подорожали на 1000
UPDATE product
SET price = price + 1000;

-- Показать изменения в таблице product
SELECT name, price
FROM product;