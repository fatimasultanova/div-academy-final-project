                             HomeMate.com
Proyektin məntiqinə gəlsək  istifadəçilərin ev elanı paylaşması və otaq yoldaşı tapması  xidmətini göstərən bir proyektden söhbət gedir (meselen https://bonpini.com/ saytına nəzər keçirə bilərsiniz proyekti daha yaxşı başa düşmək üçün şiddətlə tövsiyyə edilir ki elan nece yaradılır saytda ona baxıb və elan yaradasınız amma ki elanı paylaşmayın sadece sonra qədər prosesin nece getdiyini görün və saytı ətraflı analiz edin ).Əlavə bütün APİ enpointlər aşağıda qeyd olunacaqdır. Database modeli qeyd ediləcəkdir amaki tam qeyd edilməyəcək əlaqələri siz qurmalı olacaqsınız(dəyişiklik etmək sizdən asıldır çox kənara çıxmadan  əlavə columnlar və tableler yarada bilersiniz təqdimat zamanı isə etdiyiniz dəyişiklikləri və ya əlavələri izah edəcəksiniz.Əlavə olaraq saytda istifadəçilər başqa istifadəçilərin paylaşdığı elanları görə bilərlər lakin elan paylaşan şəxsin əlaqə məlumatlarını əldə etmək üçün ödəniş etməlidirlər bunun üçün test Payment Gateway istifadə ediləcəkdir (detallar aşağıda qeyd ediləcəkdir) 

           İstifadə olunacaq texnologiyalar & toolar 
1.	Spring Boot 3.0.0 + 
2.	Liquibase 
3.	Docker (&K8S istəyə bağlıdır )
4.	Github yada Gitlab
5.	Spring Data Jpa (with Hibernate) 
6.	Postgresql
7.	Spring Security 
8.	RestFull apis
9.	Authentication with Google 
10.	Testing (Unit testing + Api testings)
11.	MailSender
12.	JWT tokens
13.	Logging
14.	Swagger api
15.	Message Brokers (rabbitMQ , Kafka ) - optional 
16.	Caching (redis , Hibernate second level ) -optional 
                                  DB -Architecture 


Database quruluşuna  və ətraflı açıqlamalara draw.io linki vasitəsilə keçid edə bilərsiniz. ( draw io linki :  https://drive.google.com/file/d/15wZeldWbRMztdKFcLbZFum3JvOQTTw31/view?usp=sharing ) 

QEYD: Bəzi əlaqələr tam göstərilməmişdir  göstərilməyən əlaqə varsa yada filan tablede bu məlumatı da əlavə edilməlidir deyirsinizsə buyurun edin ama təqdimat sırasında qeyd etməyi unutmayın. Məsələn mən  çox böyüməsin deyə evdəki əşyaları və ya evdə  qaydaları (meselen siqaret çekmek olmaz , icki olmaz ve s.. ) əlavə etmədim ama siz istəyirsinizsə edə bilərsiniz. 

                                TEST PAYMENT GATEWAY API
Database cədvəlindəki açıqlamalara baxsanıs görəcəksiniz ki , hər bir istifadəçi abunəlik ala bilər bəs bu nə üçündür ? Tutaqki mən bir elan paylaşdım  hemin elan haqqındakı bütün məlumatları başqa istifadəçilər görə bilər lakin mənimlə əlaqə saxlamaq üçün əlaqə nömrəmi görə bilməzlər bunun üçün abunəlik almaldırılar abunəlik haqqında detallar ətraflı olaraq database cədvəlinin açıqlama hissəsində qeyd edilmişdir.
Payment xidməti göstərmək üçün biz Stripe.com apisindən istifadə edəcəyik (documentation - https://docs.stripe.com/testing?payment-method=ach-direct-debit&shell=true&api=true&resource=payment_intents&action=create&testing-method=card-numbers#use-test-cards )

Ödənişlərin keçirilməsi prosesi xarici provider terefinden olur və sizin ödənişi  etməyiniz üçün yalnızca 
https://api.stripe.com/v1/payment_intents
 Bu endpointde  content type olaraq application/x-www-form-urlencoded  seçib amount , currency və payment_method dəyərlərini göndərməyiniz tələb olunur 
Authorization headerine isə   aşağıdakı keyi göndərin
 sk_test_51P26zlL0rWx1GiH2l0TIShnMRYuZSeDnsLLBlxMkyWxYg0RF09RPpqweQELy3qNiKGy0w8Un21Z82CrKYlRmzWKj00uZutyjI7
Test üçün şəkillər - 

Stripe.com online ödənişləri etmək üçün hazır integrasiya olunmuş sistemdir.
Biz əslində online ödənişlərdə kart məlumatlarını özümüzdə saxlamırıq bunu xarici vendorlar vasitəsilə edirik bunun üçün stripe.comun apisinə kart məlumatları yerinə APİ KEY məlumatını göndəririk daha sonra curreny və amount göndərməklə fake ödəniş həyata keçir. Əgər 200 OK status kodu alsanız deməli ödəniş keçmişdir və istifadəçinin profilində abunəliyini aktiv etmək lazımdır. Birdaha qeyd edim bu sadecə fake ödəniş üçündür 
(bu prosesin real olaraq necə baş verdiyini öyrənmək istəyirsinizsə aşağıdakı linkə keçid edə bilərsiniz 

https://www.barclaycard.co.uk/business/accepting-payments/learn-about-taking-payments/how-do-online-payments-actually-work


 EDİLƏCƏKLƏR LİSTİ
1.	Database Structurunu tam qurmaq code first şəklində bütün cədvəllər liquibase migration tool vasitəsilə əlavə olunmalıdır (istəyən FlyWay-də istifadə edə bilər). Və bəzi hazır məlumatlar bu toolar vasitəsilə əlavə olunmalıdır (məsələn rollar , tiplər və s. )
2.	Bütün tablelər üçün CRUD apilər yazılmalıdır . Server 8080 portuna run olunmalıdır və api endpointlərini versiyalandırmağı unutmayın (api/v1/.....  ) apilər haqqında ətraflı məlumat aşağıda göstəriləcəkdir
3.	Authentication üçün   username pass daxil olunmaqla  və ya Google auth provider istifadə olunsun (istifadəçi həm email həm username həmdə telefo nömrəsi  vasitəsilə login ola bilər) 
4.	SignUp apisi yazılsın - sistemə qeydiyyat üçün  (istifadəçi email və ya telefon  nömrəsi ilə qeydiyyatdan keçe biler) 
5.	Lombok və  Mapstruct optionaldı (istifadə etsəniz vaxt baxımdan önə düşəcəksiniz)
6.	İstifadəçi yalnız abunəlik almışsa o zaman elan paylaşanın nömrəsini görə bilər əks halda görə bilməz və hər sorğu yadda saxlanılmalıdır.İstifadəçi əgər bir elanın nomresini görmək üçün sorğu atmışdırsa o zaman yenidən sorğu atsa həmin elan üçün yalnız 1 dəfə sorğu haqqı silinməlidir yəni eyni elana 1000 dəfədə sorğu atsa yalnız 1 sorğu haqqı silinəcəkdir
7.	Proyekt Githuba yada Gitlaba əlavə olunsun. Yaratdığınız   branchlar haqqında məlumatlar təqdimat sırasında göstəriləcəkdir. Və eyni zamanda Docker image yaradılıb dockerhuba push edilməlidir. Təqdimat sırasında proyekti docker-compose file ile run edəcəksiniz (k8s istifadə edənlər minikube və ya cloudda run etməklə deployment filelarını göstərməlidir)
8.	Ən azı 2 servisin testingi yazılmalıdır  istədiyiniz 2 servisi seçə bilərsiniz
9.	Mail ilə təsdiq - istifadəçi sistemə registrasiya olduqda email vasitəsilə təsdiqlədikdən sonra profili aktiv olmalıdır emaili təsdiqləmədən sistemə giriş edə bilməz .(user_verify tablesindən istifadə edə bilərsiniz)
10.	Bütün apilərə daxil olan sorğular , errorlar loglanmaıldır log file her gün üçün yenilenmelidir  köhnə məlumatlar isə arxivlənsin max log file ölçüsü 30 MB ,  file adı  - day-month-year.log
11.	Swagger api ile documentasiya configurasiyası yazılmalıdır  təqdimatda bunun üzərindən izah edəcəksiniz daha çox
12.	Postmanda collections yaradın təqdimat sırasında vaxt itirməyin yeni sorğu yaratmaq üçün.
13.	Api Stress-Testing toolarından istifadə edərək 10 000-dən 100 000-ə qədər sorğu ataraq proyekti test edib benchmark göstərə bilərsiniz bu optionaldır məcburi deyildir.
14.	Meselen Mail göndərmək  servisini asinxron edib  bu sorğuları queuelere elave ede bilersiniz  bunun üçün Kafka və ya rabbitMQ istifadə edə bilərsiniz bu hissə optionaldır
15.	Admin paneli üçün APİ yazılmalıdır həmçinin Moderator roluda əlavə edilsin moderatorlar sadecə istifadəçinin elanın deaktiv edə bilər ama istifadəçini silə bilməz və istifadəçi haqqında məlumatları görə bilər.
16.	Qeydiyyatdan keçen zaman sadecə User rolunda istifadəçi yaradılacaqdır ADMİN və ya Moderator rollarını sadece Admin özü yarada bilər.
17.	İstifadəçi telefon nömrəsi ilə qeydiyyatdan keçdikdə OTP təsdiq kodu üçün Telegram APİ istifadə edə bilərsiniz pulsuz SMS APİ servisləri tapsanız onuda istifadə edə bilərsiniz Optionaldır ama Mock servis yazmaq vacibdir sanki mesaj göndərirsiniz və istifadəçi  kodu təsdiqləyir.

APİs

ADMİN paneli üçün  APİ yazılsın rolu admin olan istifadəçi 
1.İstənilən istifadəçini silmək (deaktiv etmək) və ya edit etmək 
2. İstənilən elanı silmək və ya edit etmək
3.Bütün elanları paylaşan şəxsin nomrəsini gorə bilər limit yoxdur
4.Yeni Admin rolunda olan istifadəçi və ya Moderator yarada bilər (Adminləri də idarə etmək üçün SuperAdmin rolu əlavə edə bilərsiniz) 
USER rolunda olan istifadəçilər isə yalnızca  özü haqqında olan məlumatları görə bilər digər istifadəçilərin elanlarını silə və ya edit edə bilməz
 
Digər bütün  entitylər üçün isə CRUD əməliyyatlar yazılmalıdır (bizness məntiqi qorunmaqla ) 


Təqdimat sırasında hansı problemleri görüb onu nece aradan qaldırdığınız barede(meselen burada birinci bele yazmisdim sonra bu problemə görə dəyişdim )  + istifadə etdiyiniz texnologiyaları necə və nə üçün istifadə etdiyinizi bununla hansı problemi aradan qaldırığınız + postmanda hazır olan collectionlarla sorğu göndərib və logglarda prosesi ətraflı izah etməlisiniz(öncədən hər şey test edilib yadda saxlanılsın təqdimat vaxtı hamısını sıfırdan etmək çox vaxt ala bilər ) . Yəni qısaca develop və test mərhələsini izah edəcəksiniz.

Proyektin main ideasını qorumaq şərtilə istədiyiniz  dəyişiklik və ya əlavələri edə bilərsiniz.

Hər birinizə uğurlar.






