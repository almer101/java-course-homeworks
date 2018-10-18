HRVATSKI-Upute
	Cilj aplikacije je sakupljanje primjera rukopisa za potrebe izgradnje aplikacije koja prepoznaje rukom pisane boolean jednadžbe. 

	Aplikacije pokazuje izraz koji je potrebno napisati. 
	Crveno označen simbol u izrazu je onaj kojeg je potrebno trenutno unijeti. 
	Simbol se unosi/crta povlačenjem pritisnutog lijevog klika miša na 'platnu' za unos koje se nalazi ispod platna s prikazanim izrazom. 
	Nakon unosa označenog simbola potrebno je pritisnuti desnu tipku miša kako bi se označio kraj unosa tog simbola.

	Brisanje cijelog trenutnog unosa moguće je obaviti akcijom CLEAR. (CTRL+SHIFT+C)
	Brisanje zadnjeg unosa moguće je obaviti akcijom UNDO. (CTRL+Z)

	Pohrana napisanog izraza obavlja se akcijom "STORE EXPRESSION". (CTRL+S)

	Nakon pohrane prikazuje se idući izraz kojeg je potrebno unijeti ili ako su uneseni svi izrazi aplikacija se gasi uz zahvalu na doprinosu.
	Broj preostalih izraza za unos vidljiv je u gornjem desnom kutu aplikacije. 
	 
	Nakon unosa svih izraza pošaljite datoteku: "./data/boolean_gesture_recognition_other.mv.db"
	na adresu: petar.afric@fer.hr
	U slučaju kritične iznimke možete poslati i sadržaj "./logs/" foldera.

	Aplikacija nudi i management tab unutar kojeg je moguće pregledati unesene izraze.
	Ako ste pogreškom pohranili pogrešan izraz u ovom tabu možete ga ukloniti iz baze podataka. 
	Ako izraz nije odmah vidljiv akcijom RELOAD možete izvesti osvježenje podataka iz baze.

ENGLISH-instructions
	The goal of this application is to acquire handwriting examples which will be used in development of an application which can recognize boolean expressions.

	The application shows the expression which needs to be written.
	The symbol in the expression written in red is the one that needs to be currently written.
	The symbol is written by dragging the left mouse click on the input 'canvas' which is located under the expression demonstration canvas. 
	 After writing the current symbol press the right mouse click to signal the current symbol has been entered.

	All current input can be erased by using the CLEAR action. (CTRL+SHIFT+C)
	The last input can be undone by using the UNDO action. (CTRL+Z)

	Storing the expression is done by using the "STORE EXPRESSION" action. (CTRL+S)
	
	After storing the next expression is shown or if all expressions have been written the application thanks the user and closes.
	The number of expressions which need to be entered is visible in the upper right corner of the application. 

	After writing all the expressions send the "/data/boolean_gesture_recognition_other.mv.db" 
	file to: petar.afric@fer.hr
	If a critical error occurred you can also send the content of the "./logs/" folder

	The application also offers a management tab which can be used to inspect the written expressions.
	If you have accidentally stored a faulty expression you can delete it from the database inside this tab. 
	If the expression is not visible you can refresh the tab content using the "RELOAD" action. 
