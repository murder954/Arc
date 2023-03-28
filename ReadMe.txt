--------------------------------------------------------------------------------------------------------------------------------------------------------------
 
 Команды для запуска.
 //каждая команда принимает параметр [dataProvider] для выбора провайдера. Допустимые значения: DB; CSV; XML
-------------------------------------------------------------------------------------------------------------------------------------------------------------- 
 
 
 //создание владельца
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -owner [dataProvider] [ownerName] [phoneNumber] [email] [bankAccount]
 
 //Пример:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -owner DB Ryan_Gosling +79885981523 drivegos@gmail.com 7173204823793022
-------------------------------------------------------------------------------------------------------------------------------------------------------------- 


 //создание питомца. Для создания питомца необходимо указать идентификатор владельца. В зависимости от типа питомца отличается тип команд.
 //общие параметры для всех типов питомцев:
 ownerId - идентификатор хозяина
 petName - имя питомца
 gender - пол питомца
 weight - вес питомца
 feedType - тип еды питомца
 petType - тип питомца
 age - возраст питомца
 diseaseName - название болезни
 
 //создание кота:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -pet [dataProvider] [petType] [ownerId] [petName] [gender] [weight] [feedType] [age] [diseaseName] [afraidDogs] [isCalm]
 
 //специальные параметры для создания кота
 afraidDogs - булевое значение, показывающее отношение кота к собакам
 isCalm - булевое значение, показывающее спокойный питомец или нет
 
 //Пример:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -pet DB cat a5ccdaaa-cdd3-45f6-8e55-1814fa2545c3 Paul male 2.8 wiskas 4 pancreatitis true true
-----------------------------------------------------------------
 //создание собаки:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -pet [dataProvider] [petType] [ownerId] [petName] [gender] [weight] [feedType] [age] [diseaseName] [isAgressive]
 
 //специальные параметры для создания собаки
 isAgressive - булевое значение, показывающее агрессивная ли собака
 
 //Пример:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -pet XML dog c9543e82-3d39-4460-9265-5074cd440d18 Vi male 33.4 meatsticks 2 cystitis true
-----------------------------------------------------------------
 //создание птицы:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -pet [dataProvider] [petType] [ownerId] [petName] [gender] [weight] [feedType] [age] [diseaseName] [isWaterFlow]
 
 //специальные параметры для создания птицы
 isWaterFlow - булевое значение, показывающее водоплавающая ли птица
 
 //Пример: 
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -pet XML bird c9543e82-3d39-4460-9265-5074cd440d18 Flavio female 2.4 bread 2 psitacosis false
-----------------------------------------------------------------
 //создание рыбки:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -pet [dataProvider] [petType] [ownerId] [petName] [gender] [weight] [feedType] [age] [diseaseName] [waterType]
 
 //специальные параметры для создания рыбки
 waterType - тип воды для обитания рыбки
 
 //Пример: 
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -pet CSV fish 314eb43f-ac8f-4b44-a313-90d1e09d5996 Goldy female 0.3 corn 1 lepidorthosis fresh
--------------------------------------------------------------------------------------------------------------------------------------------------------------


 //создание записи корма
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -feed [dataProvider] [feedName] [priceForPack] [weightOfPack] [forPetType]
 
 feedName - название корма
 priceForPack - цена за 1 пачку(double)
 weightOfPack - вес пачки(double)
 forPetType - тип питомца(enum: CAT, DOG, FISH, BIRD), для которого предназначен данный корм
 
 //Пример:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -feed XML wiskas 145.2 100.0 CAT
--------------------------------------------------------------------------------------------------------------------------------------------------------------
 
 
 //создание записи болезни
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -disease [dataProvider] [nameOfDisease] [treatmentTime] [forDisease]
 
 nameOfDisease - название болезни
 treatmentTime - время лечения
 forDisease - лекарство для лечения болезни
 
 //Пример:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -disease DB cystitis 10 furadonin
--------------------------------------------------------------------------------------------------------------------------------------------------------------
 
 
 //создание записи лекарства
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -drug [dataProvider] [drugName] [price] [piecesInPack] [intensityPerDay]
 
 drugName - название лекарства
 price - стоимость одной пачки
 piecesInPack - количество таблеток в пачке
 intensityPerDay - необходимое количество лекарства для принятия в день 
 
 //Пример:
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -drug DB furadonin 346.99 12 2
--------------------------------------------------------------------------------------------------------------------------------------------------------------
 
 
 //создание записи варианта жилья
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -variant [dataProvider] [houseName] [insideHouseUsing] [environmentFeatures] [addition] [price] [forPetType]
 
 houseName - название варианта жилья
 insideHouseUsing - булевое значение, показывающее для уличного или домашнего испольования предназначен вариант
 environmentFeatures - особенности варианта жилья
 addition - дополнение к варианту жилья, которые включены в стоимость 
 price - стоимость варианта жилья
 forPetType - тип питомца(enum: CAT, DOG, FISH, BIRD), для которого предназначен данный вариант жилья
 
 //Пример:
 java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -variant CSV big_cage true perch bird_feeder 3999.99 BIRD
--------------------------------------------------------------------------------------------------------------------------------------------------------------
 
 
 //удаление записей
 //Команды для удаления записей отличаются в зависимости от
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -delete [dataProvider] [recordType] [recordId]
 
 recordType - тип записи(owner, pet, feed, envvar, disease, drug)
 recordId - идентификатор записи
 
 //Пример:
 //удаление владельца(вместе с владельцем будут удалены все питомцы этого владельца)
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -delete CSV owner 314eb43f-ac8f-4b44-a313-90d1e09d5996
 
 //удаление питомца
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -delete XML pet ba7d5951-a30c-48a3-b8e9-64c8eccb1485
 
 //удаление корма
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -delete DB feed ef56774c-1eca-4eea-8850-38f2c91c59ca
 
 //удаление варианта жилья
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -delete DB envvar 251a28ff-b232-49a6-94dc-08f78264368f
 
 //удаление болезни
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -delete XML disease f1981e39-9dc6-4981-812d-80a2e6305748
 
 //удаление лекарства
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -delete CSV drug 174bf1ab-963a-470a-b35a-4cf1916b369c
--------------------------------------------------------------------------------------------------------------------------------------------------------------
 
 
 //лечение животного(создание сервиса лечения животного, и сохранение записи о посещении вет клиники в историю)
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -treatment [dataProvider] [petId]
 
 petId - идентификатор животного
 
 //Пример:
 //DataBase Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -treatment DB 478c99e7-e208-449a-bc0d-d51751776fb6
 
 //XML Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -treatment XML ba7d5951-a30c-48a3-b8e9-64c8eccb1485
 
 //CSV Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -treatment CSV 7dd91e7a-fa19-4773-b760-2c1dcd80f2cf
--------------------------------------------------------------------------------------------------------------------------------------------------------------
 
 
 //подбор корма для питомца
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -selectFeed [dataProvider] [petId] [needToBuy] [piecesToBuy]
 
 petId - идентификатор животного
 needToBuy - булевое значение, которое показывает будет ли последущая покупка корма или лишь подбор корма
 piecesToBuy - количество пачек для покупки
 
 //Пример:
 //DataBase Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -selectFeed DB 478c99e7-e208-449a-bc0d-d51751776fb6 true 2
 
 //XML Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -selectFeed XML ba7d5951-a30c-48a3-b8e9-64c8eccb1485 true 2
 
 //CSV Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -selectFeed CSV 7dd91e7a-fa19-4773-b760-2c1dcd80f2cf true 2
--------------------------------------------------------------------------------------------------------------------------------------------------------------


 //подбор варианта жилья для питомца
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -selectVariant [dataProvider] [petId] [needToBuy]
 
 petId - идентификатор животного
 needToBuy - булевое значение, которое показывает будет ли последущая покупка варианта или лишь его подбор 
 
 //Пример:
 //DataBase Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -selectVariant DB 478c99e7-e208-449a-bc0d-d51751776fb6 false
 
 //XML Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -selectVariant XML ba7d5951-a30c-48a3-b8e9-64c8eccb1485 false
 
 //CSV Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -selectVariant CSV 7dd91e7a-fa19-4773-b760-2c1dcd80f2cf false
-------------------------------------------------------------------------------------------------------------------------------------------------------------- 
 
 
 //подсчет затрат на питомца на основе истории обращений в клинику
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -expenses [dataProvider] [petId]
 
 petId - идентификатор животного

 
 //Пример:
 //DataBase Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -expenses DB 478c99e7-e208-449a-bc0d-d51751776fb6
 
 //XML Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -expenses XML ba7d5951-a30c-48a3-b8e9-64c8eccb1485
 
 //CSV Provider
  java -Dpath=src/main/resources/MyAPP.properties -jar out/artifacts/Lab1Logs_jar/Lab1Logs.jar -expenses CSV 7dd91e7a-fa19-4773-b760-2c1dcd80f2cf
--------------------------------------------------------------------------------------------------------------------------------------------------------------
 
 
 
 
 
 
 
 
 
 
 
 
 
