# VideosDB
                  -----------------> 322 CD - > CODERIE ANDRE - LUCIAN <-----------------
Platforma simplificata care ofera informatii despre seriale si filme.


In main am toata logica programului. Aici initalizez intai liste de useri, actori,
movie si seriale folosindu-ma de input. Dupa care verific ce tip de instructiune a
fost data, daca este data comanda, trec prin tipurile de comenzi. In output scriu
folosindu-ma de metoda implementata in schelet writeFile( id, * string * , returnOfFunction)
, la fel si pentru query si recomandations.
In clasa tasks am taskurile implementate, in niste clase care au fost initial copiate
din packetul fileio, respectiv clasele date in schelet * initial credeam ca nu aveam
voie sa completam in clasele acelea, de asta am vrut sa-mi fac eu clasele chiar daca
este o pierdere de memorie si cod duplicat *.

    -----------TASKURI-----------

Comenzile sunt implementate in clasa User din pachetul tasks.


 -> Favorite : Pentru a adauga un videoclip la favorite ale unui user verific intai daca
 acel videoclip este inclus in isotricul userului respectiv. Daca nu este, inseamna
 ca videoclipul nu este vazut, iar daca este inseamna ca pot sa-l adaug la favorite.
 Verific daca titlul videoclipului dat este in lista de favorite, daca nu, il adaug,
 iar daca da inseamna ca videoclipul este deja adaugat la lista de favorite.

 -> View : Pentru functia asta am facut un fel de setter pentru istoricul unui
 user, pentru a adauga in mapa aceea.Parcurg lista de useri, daca gaseste
 user-ul dat, verifica daca in istoric are titlul dat, daca are, la valoarea
 din map a titlului mai adaga unul, in cazul in care in istoricul userului
 titlu nu a fost gasit, se pune titlul in map cu valoarea 1.

 -> Rating : Parcurg lista de user si lista de filme in acelasi timp (nested loop),
 verific daca uerul este userul dat in input si daca movie are titlul dat in input.
 Aici am adaugat campul in movies ratedVideo care este o lista de double, unde
 sunt retinute valorile primite in ratinguri, iar in user am facut un camp care
 retine numele titlului care a dat rate, fiind o lista de stringuri. Daca in lista asta
  se gaseste titlul dat in input, respectiv titlul filmul inseamna ca fimul a primit
 deja un rate de la userul respectiv, daca nu acest titlu este adaugat in lista, si
 se iese astfel din loop adaugand mesajul respectiv campului message.
 Inca un camp adaugat in clasa User este nrOfRatings, care este un int in care se
 retine cate ratinguri a dat userul respctiv * voi avea nevoie mai incolo *.
 Daca s-a ajuns aici, adaug in lista de stringuri ale userului titlul filmului
 si in lista de ratinguri din Movie, ratingul primit si in message pun mesajul
 dorit la afisare. Daca titlul nu se afla printre cheile din mapa de istoric a
 userului inseamna ca videoclipul nu a fost vazut.
   
   -----------QUERY-URILE-----------
   
----------> pentru actori

Query-urile pentru actori sunt in taska Actor din pachetul tasks.

-> Average: Pentru acest task am creeat o metoda abstracta getAverageOfRatings
in clasa abstracta Show, pentru a fi prelucrata intr-un mod de un film si in alt
mod de serial. In mare aceasta metoda face media pentru ratinguri pe film, respectiv
serial tinand cont de sezoane.
Initial am creeat o mapa care contine toti actorii si la care am adaugat
initial valoarea 0.0. Parcurg lista de actori si lista de movies(nested loops) si caut
videorile care apar in filmogorafia fiecarui actor. Dupa care compar daca valoarea din
mapa creeata initial este mai mica decat decat media de ratinguri de la filmul respectiv,
aceasta medie se adauga in mapa. Aici am un contor pentru a verifica daca actorul respectiv
are mai multe filme cu rating. Initial tot ce ajunge aici respecta conditia, initial
toate avand 0.0 si nefiind posibil un rating negativ sau egal cu 0.0. Daca contorul este
mai mare decat 1(daca actorul are mai multe video cu rating) pun in mapa media acestora.
Pentru a calcula media acestora am facut o alta metoda in Actor care calculeaza media.
*functie(List<Movie> movies).
Dupa ce am terminat de parcurs lista de movie, fac la fel si pentru serial, mai usor insa,
deoarece metoda de average a serialelor este mai buna si mai scuteste de efort.
Dupa care sortez mapa intai dupa valori, dupa care dupa cheie pentru a fi organizat si
alfabetic si pun totul intr-o lista, ma ajut de stream pentru a-mi fi mai usor.
Creez si o lista cu valorile 0 din mapa si elemin
din lista sortata toata lista cu valori 0. Creez o lista inversa cu lista sortata
pentru a-mi fi mai usor in cazul in care sort typul este descendent.
Si am trecut pe cele 2 cazuri in care sortType este de 2 feluri, tinandu-se cont,
bineinteles de numarul limita de actori intorsi, dat in input.

->Awards : Implementarea de seamana foarte mult cu average si anume : Creez o lista in
care adaug toti actorii care indeplinesc conditia de a avea size ul liste de award uri
mai mare sau egala cu size-ul liste de awarduri din filtru. Fac o mapa in care pun
numele actorului si numarul de awarduri primit. Pentru a calcula numarului de acwarduri
primit am facut o metoda separata unde am toata logica ei. Elimin din mapa orice element
care are valoarea 0. Sortez mapa mai intai dupa valori, dupa care alfabetic si pun rezultatul
intr-o lista, la fel ca la average. Generez alta lista cu actori care nu indeplinesc
conditiile de filtrare, dupa care din listea mea sortata din map elimin elementul
care este si in lista cu cei care nu indeplinesc. Mai fac niste verificari prin lista
sortata in cazul in care vreun filtru de verificare a trecut cumva. In final
ajung la cele 2 conditii, anume daca sortTyup este ascendent sau descendent.

->Filter Description : Aici am pus descrierea unui actor intr-o noua lista ,inlocuind cuvantul
award cu unul usor descifrabil. Am eliminat cuvantul usor descifrabil care mai avea caracterul
"s" la final, acest lucru venind de la awards si nedorind ca filterul de award sa traca si
pentru cuvantul awards. Am facut acest lucruo deoarece in listra de filtre pentru descriere
erau cuvintele "war" si "award", singurele care faceau probleme, deoarece war intra in compozitia
lui award. Puteam face chestia asta parcurgand lista de filtre si in cazul in care un element
este in compozitia altuia, sa schimb elementul ala intr-un cuvant usor descifrabil, dar in cazul
de fata fiind doar 2 cuvinte problematice, e suficient sa schimb unul si s-a rezolvat. In continuare
parcurg prin lista de actori si verific daca descrierea fiecarui are toate elementele din filters,
in caz afirmativ pun numele actorilor intr-o lista, pe care o sortez alfabetic. Ajungand in final
la conditiile pentru sorttype si anume ascendent si descendent, unde este in mare parte si
ca la celelalte querys. Daca este ascendent in lista finala pun actorii sortati ascendent, daca nu
invers.

----------> pentru video-uri
  
Aceste taskuri sunt implementate in clasele Movie, respectiv Serial.

-> Rating : Parcurg lista de movie-uri, punand intr-o mapa titlul fiecarui film si averagerating-ul
acestuia. Dupa care ma folosesc de stream pentru a sorta aceasta mapa dupa value, si anume dupa
average rating, iar apoi alfabetic, dupa cheie. Rezultatul  final il pun intr-o lista. Iterez
iar prin lista de filme pentru a verifica ce filme au average 0 si pentru a le scoate din
lista sortata. Creez o lista si pentru sortarea descendenta, pentru a-mi mai usor in cazul ei.
Ajuns la sort type, iterez prin lista de filme si verific ca filtrele sa fie atat eligibile
cat si filmele a caror nume le-am gasit in lista mea sortata sa respecte conditiile de filtre.
Cele care au respectat aceste conditii sunt puse intr-un array final. Am tinunt cont la final
de numarul maxim de filme care trebuie intors, iterand prin array final doar pana la n in cazul
in care size-ul array-ului este mai mare decat n.
Pentru rating-ul pe seriale este cam acelasi algoritm intrucat metoda de averageActors calculeaza
average pentru film cat pentru serial in mod individual.

-> Favorite : Am pus intr-o mapa toate titlele posibil de filme, la care initial le-am pus valoarea 0.
Iterez prin mapa de useri, si filme (nested loop) si in cazul in care in lista de favorite a unui user
exista un film adaug unu la valoarea titlui filmului respectiv din mapa. Pentru ca aveam cod duplicat
in multe metode, am creeat o metoda care scoate din mapa cheile care au valoarea 0, si care sorteaza
mapa intai dupa valoarea, apoi dupa chei, in ordine alfabetica, iar rezultatul il pune intr-o lista.
Avand lista sortata ascendent, o creez si pe cea pentru descendent si astfel ajung la verificarea
sorttype-ului. Trec prin cele 2 cazuri ale sorttype-ului, verificand ca filtrele pentru favorite
sa fie eligible, cat si ca filmele al caror nume le am sortate ascendent/ descendent in cele 2
liste sa respecte acele filtre. La final tin cont de n, in cazul in care size-ul array-ul final
incare am pus totul dupa filtrare este mai mare se itereaza pana la n si se intoarce ce este pana
la n, in caz contrar se introare tot finalArray.
Pentru seriale este acelasi cod, neavand pentru acesti query diferente.

-> Longest : Creez o lista in care pun toate numele filmelor, respectiv durata fiecaruia. Sortez
aceasta mapa dupa valoare, dupa care dupa cheie in ordine alfabetica si rezultatul cheilor il pun
intr-o lista, care pentru filme este si lista dorita. Ajuns la cele 2 cazuri de sort type, verific intai
daca filtrele date in input sunt eligibile, daca toate sunt null, in final Array pun toata lista sortata
daca filmele a craror nume le-am sortat in lista de mai sus respecta filtrele date in input. Numele
celor care le respecta sunt puse intr-un finalArray care tine numele finale. La final am tinut
cont de n, verificand daca size-ul array-ului final este mai mare decat n.
Pentru seriale am creeat o metoda care returneaza durata unui serial *suma tuturor duratelor unui sezon*.
De aici restul algoritmului este ca cel pentru un film

->Most Viewed : Creez o mapa in care pun numele filmui si numarul de vizionari ale acestuia, adaugand la cheie
valoarea noua, in cazul in care titlu este prezent deja in map. Dupa care sortez dupa valoarea, apoi alfabetic
si rezultatul il pun intr-o lista. Lista rezultata este lista buna, astfel creez si o lista descendenta pentru
a-mi fi mai usor in cele 2 cazuri din sortType. In sortType verific daca filtrele sunt eligible si daca
filmele ale caror nume le am in cele 2 liste sortate verifica acele filtre. Daca da, pun titlurile intr-o
noua lista, o lista finala. La final verfic daca size-ul liste finale este mai mare decat n, daca nu
se va intoarce toata lista, daca da se va itera pana la n si ce a fost gasit se va intoarce.
Pentru seriale algoritmul este asemanator, acestea neavand diferente pentru acest task.

----------> pentru useri
  
Acest task este implementat in clasa User.

->Number of ratings : Pun intr-o mapa numele utilizatorul si nr de ratinguri date *vezi metoda de care am zis
mai sus ca o sa am nevoie*. Folosindu-ma de metoda la care am zis ca am cod duplicat si am creeat o metoda
pentru a pune valorile 0 din mapa intr o lista si a pune rezultatul sortat dupa valoare, si
dupa chei, alfabetic. Din lista sortata arunc lista cu valorile 0. In sfarsit ajung la cele 2 cazuri
de sort type. Aici nu exista filtre deci in final array pun efectiv lista sortata ascendet respectiv descendent.
La final tin cont de N dat.

    -----------COMENZILE-----------
  
Comenzile sunt implementate in clasa user.

-----------> pentru useri basic

-> Standard Rec. : Iterez prin lista de filme, respectiv useri. In cazul in care user-ul are username
equal cu username dat in input, iar in istoricul acelui userl filmul nu este prezent intr-o lista finala
adaug titlul acelui film, dand break dupa acesta. La fel fac si pentru seriale, doar ca iterez prin lista
de seriale, evident. La final adaug un camp in lista de final, daca acel camp adaugat este pe prima pozite
inseamna can u a fost gasit nimic, daca nu se intoarce titlul care se afla pe prima pozitia din array-ul final.

-> Best unseen : Creez o mapa in care pun titlu fimlului respectiv serialului si average-ul sau. Ma folosesc
de metoda sortByValue pentru a sorta acea mapa si a pune intr-o lista. Din acea lista elimin titlele
care au valoarea 0 in mapa creeata anterior si filmele sau serialele din istoricul userului
care are ca username cel dat in input. La final interez prin listele de seriale si de filme, respectiv
lista sortata de filme, si se alege primul video din sortedMap care si apare primu in lista de movie
sau seriale

-----------> pentru useri premium

-> Popular : La inceput am facut vericari ca user-ul dat in input sa aiba si abonament premium.
Am creeat o mapa in care o sa pastrez numele genului si numarul de vizionari al genului
respectiv. Initial am creeat mapa cu toate genurile posibile, la care am pus valoarea 0.
Iterez prin lista de useri, dupa care prin cea de movie respectiv cea de seriale si in cazul
titlul filmului/ serialului este inclus in mapa de history a userului exista numele filmului,
se itereaza prin lista de genuri a video-ului si se pune in mapa la cheia de la genul i,
valoarea din mapa a genului i + valorea din user.getHistory a video-ului respectiv. Se sorteaza
totul printr-o noua metoda creeata * asemanatoare cu una precedenta, doar ca au tipul din map
diferita* sortbyValue, care face si ce zice numele sorteaza un map dupa valoare.
In final se pune totul sortat intr-o lista.
Ajungem la partea finala unde se itereaza prin useri, iar daca user-ul este user-ul dat in input,
atunci se sorteaza dupa lista de genuri sortata mai devreme, respectiv dupa lista cu toate video-urile.
Daca video-ul are genul s din lista de genuri, si daca in istoricul userului nu exista titlui
video-ului respectiv, atunci in lista finala se pune titlul videoului, dandu-se astfel break.
Dupa ce s-a terminat for-ul adaug in lista finala un element, iar daca primul element
este acela, inseamna ca Popular rec nu a mers, daca nu se intoarce primul element din final Array.

-> Favorite : Din nou, se fac verificari pentru a vedea daca userul are abonament premium sau nu.
Algoritumul este foarte asemanator cu cel de la popula recommandation, doar ca
in loc de verificat si pusa in mapa din mapa de istoric, se va pune din lista de favorite. Iar
in mapa se va incrementa cu unu in cazul in care acel video apare de mai multe ori intr-o
lista de favorite. Dupa ce am terminat de populat acest map, se sorteaza dupa value si alfabetic.
Se elimina din lista video-urile care nu sunt in nicun favorite List prezente.
Dupa care se incepe iar iterarea prin lista de movie uri si de seriale pana se da de primul element
care nu este in map-ul de istoric al user ului. si se pune in finalArray. Daca sa zicem sunt
primele 3 element cu acelasi numar de aparitii favorite. Se va lasa decat ultimul ca fiind cel bun
(intai se adauga practic ultimul din acea lista de 3, iar acela returnat). Dupa incheierea forului
se adauga in finalArray un element, iar daca primul element este acela, favorite rec nu se poate aplica,
in caz contrat se intoarce primul element din finalArray.

-> Search: Se verifica daca user-ul are abonament premium pentru a putea aplica aceasta recomandare.
Am facut o mapa in care am stocat toate numele de video-uri si average-ul corespunzator fiecaruia.
Rezultatul l-am sortat dupa valoare, respesctiv dupa cheie si am pus cheile sortate intr-o
lista, din care am eliminat toate titlurile care apar si in istoricul userului. Dupa care din lista
aceea am eliminat totate titlurile de video care nu continul genul dat in input. Am creeat
finalArray-ul ca fiind acea lista sortat ramasa dupa acele eliminari. Daca size-ul array-ului
final este 0 atunci SearchRec nu se poate aplica. Se sorteaza alfabetic acea lista si se intoarce.



