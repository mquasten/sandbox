1) Proxy 
Als Sarah Palin m�chte ich verhindern das sich gleichgeschlechtliche Personen miteinander vern�gen.

Gegeben: 
-Interface Kissable 
-abstracte Klasse Person, die Kissable implementiert 
-Klassen Boy and Girl, die von Person erben
-PersonService als Interface und BasicPersonServiceImpl, die Kissable Instanzen f�r Boy und Girl erzeugen


Aufgabenstellung:

1.1)  KissProtectionProxyImpl (analog dem GoF-Pattern) erstellen, Bei Aufruf von "Kiss public final String kiss(final Kissable kissed)"
 wird eine Exception geworfen,wenn: kissed.type() == this.type , sonst wird an die kiss Methode des Objektes delegiert.
 
1.2) DynamicKissProxyImpl erstellen: Bei Aufruf von "Kiss public final String kiss(final Kissable kissed)"
 wird eine Exception geworfen,wenn: kissed.type() == this.type , sonst wird an die kiss Methode des Objektes delegiert.

1.3) DynamicProxyPersonServiceImpl: Dynamic-Proxies f�r Girl und Boys erstellen, 
Service erbt von BasicPersonServiceImpl: �berschreiben der Methode: "protected Kissable proxy(final Kissable person)".


3) Das gleiche f�r CGLIB-Proxies: Proxy und CGLIBProxyPersonServiceImpl erstellen



2) Strategy

Als Anwender einer math. Funktionsbibliotek  m�chte ich bestimmte Integrale numerisch l�sen 
und dabei unterschiedliche (auch selbst implementierte) Algorithmen (Simpson-, Trapez-Regel usw.) verwenden k�nnen.


Gegeben:
- ServiceInterface "double area(double min, double max, int n)" zur Integration reeller Funktionen. 
- Interface RealFunction  double result(double value) zur Berechnung des Funktionswertes einer reelen Funktion,
- Implementierungen f�r RealFunktion: Parabel und BshRealFunctionImpl 


Trapezformel:

Streifenbreite h= (b-a)/n 
St�tzstellen xk= a+k*h (k=0,1,...,n) 
St�tzwerte: y0, y1,...,y2n

s1= y0 + yn , s2=y1+y2+...+yn-1

integral=(1/2*s1+s2)*h


Simsonsche Formel:

Streifenbreite h= (b-a)/n 
St�tzstellen xk= a+k*h (k=0,1,...,2*n) 
St�tzwerte: y0, y1,...,yn

Streifenbreite h= (b-a)/(2*n)

s0=y0+y2n
s1=y1+y3+...+y2n-1
s2=y2+y4+...+y2n-2 

integral=/s0+4*s1+2*s2)*h/3


Fehlerabsch�tzung (2*n is durch 4 teilbar):

deltaI=1/15*(integral(h) - integral(2*h))

integral =integral(h) + daltaI


Aufgabenstellung:

2.1 Zu Implementieren sind SimpsonIntegrationServiceImpl und TrapezIntegrationServiceImpl (f�r Simpson bzw. Trapez-Regel).
Beide verwenden das Interface IntegrationService

2.2. Es ist ein universeller Mechanismus zur Erstellung von Java-Beans 
und das automatische Setzen der Abh�ngigkeiten zu erstellen : SimpleBeanFactoryImpl 
SimpleBeanFactoryImpl implemtiert das Interface BeanFactory.


3 State

Als MusikProduzent m�chte ich, das Pop-S�ngerinnen einem bestimmten vorgefertigten Verhalten, abh�ngig von 
ihrem Karrie-Status entsprechen, um sie mit vorgefertigten Maketing-Strategien vermarkten zu k�nnen und
m�glichst leicht Geld an ihnen zu verdienen.


Gegeben:

- Interface ArtistState

- Enum ArtistStateFactory, f�r alle m�glichen Stati wird eine Status-Klasse erzeugt.

- Interface Checker, dessen Implentierungen die Status�berg�nge vom aktuellen in den FolgeStatus ermittelt

- RandomCheckerImpl als Implementierung von Checker 
  und InnerClass f�r StayOnStateChecker als konkrete Implementierungen von Checker
  RandomChecker w�hlt zuf�lling nach Wahrscheinlichkeiten:  50%  CheckResult.Stay 25% CheckResult.Success 25% CheckResult.Failed,
  StayOnStateChecker liefert immer CheckResult.Stay (wird f�r die finalen Zust�nde verwendet)
  
 - Main-Klasse StupidGirls um den LifeCycle simmulieren zu k�nnen 
   und die Anzahl der durchlaufenen Stati, den EndStatus und das Einkommen auszugeben


AufgabenStellung:

Zu implementieren sind alle StatusKlassen des LifeCycles von female Artist (die Namen sind durch ArtistStateFactory vorgegeben). 
Gemeinsam von allen StatusKlassen genutzer Code sollte in  eine (abstrakte)  Oberklasse ausgelagert werden. 
Alle Statusklassen verwenden ArtistState. 

Nachfolgender LifeCycle soll realisiert werden:

Klasse					name		Stay		Success		Failed		amount
------------------------------------------------------------------------------
UnkownArtistStateImpl	unkon		unkown		blessed		out			0	
BlessedArtistStateImpl	blessed		blessed		hot			out			10e3
HotArtistStateImpl		hot			hot			famous		breaked		500e3
FamousArtistStateImpl	famous		famous		Inmortal	breaked		10e6
InmortalArtistStateImpl	inmortal	inmortal	-			-			0
OutArtistStateImpl		out			out			-			-			0
BreakedArtistStateImpl	breaked		breaked		hot			out			0


4 + 5 )  Als CO des Flugzeugtr�gers "USS Erich Gamma"  m�chte ich , das  f�r die vom Tracking Radar erfa�ten Flugzeuge Feuerleitl�sungen errechnet werden,
und die Gesch�tze automatisch auf die Ziele ausgerichtet werden.


4) Observer:

Als Feuerleitoffizier des Flugzeugtr�gers "USS Erich Gamma" m�chte ich, 
das die vom Tracking-Radar erfa�ten Koordinaten  der Flugzeuge automatisch  zur Errechnung einer Feuerleitl�sung (Positionsparameter des Gesch�tzturms 
f�r die Bek�mpfung der entsprechenden Ziels) f�hren. Unter Koordinaten sind Lattitude, Longitude und Altitude des Ziels zu verstehen.
Als Positionsparameter f�r das Feuerleitsystem werden der Winkel des Waffenturms zur Nordrichtung, 
die Elevaltion des Gesch�tzrohres (bei gerader Flugbahn des Geschosses), die Entfernung des Zieles �ber Grund vom Schiff 
und die direkte Entfernung des Schiffs zum Ziel ben�tigt.
Ziele mit einer Entfernung > 100 km   sind f�r das Waffensystem mangels Reichweite nicht von Interesse. 

Gegeben:

-Angle Klasse zur Representaion von Winkeln  
-Location Interface f�r Koordinaten (Longitude, Lattitude und Altitude, LocationImpl als konkrete Implementirung des Interfaces
-Aircraft Interface zur Beschreibung eines Flugzeugs und seiner Koordinaten
-Mig29 als konkrete Implementierung von Aircraft
-TargetAware Interface zur �bergabe von Flugzeugen und den entsprechenden PositionsParametern an die Waffe
-Interface Subject f�r ObserverSubject
-Interface Observer f�r Observer 
-Interface PositionCalculator zur Berechnung der Positionsparameter der Waffe, PositionCalculatorImpl als konkrete Implementierung
-SwingGui zur Simmulation von Radar und Waffe

Es wird nicht die ObserverImplemntierung von Java verwendet (da sie Unsauber ist), darum die eigenen Interfaces f�r Subject und Observer

AufgabenStellung:
Realisierung des Observermusters: Vom Radar getracktes Flugzeug  (AircraftSubjectImpl) ist ObserverSubject,
das Waffensysten (AircraftObserverImpl) ist Observer
- AircraftSubjectImpl erstellen: implements Subject und  Aircraft. 
  Die Koordinaten werden von einer �ber Strategy austauschbaren konkreten Implementierung (Mig29) geliefert.
  Die Berechnung der Positionsparameter erfolgt �ber eine per Strategie austauschbare Implementierung (PositionsparameterImpl)
  Equals und HashCode sind zu implemtieren.
- AircraftObserver erstellen: implements Observer und TargetsAware.
  Als Abweichung zum Observer der GoF k�nnen einem Observer n Subjects (Flugzeuge, Ziele) zugeordnet sein. 
  Die Bek�mpfung erfolgt dann entweder durch unterschiedliche Gesch�tze oder zeitlich nacheinander, was nicht von Relevanz ist)
  Die Zuordnung der Subjects zu den errechneten Positionsparametern  wird als private final Map<Aircraft, Position> positions abgebildet und
  �ber TargetAware vom Feuerleitsystem gelesen und weiterverarbeitet.
  
 
Anmerkung zur Bestimmung der Positionsparameter der Waffe: 
Die Entfernungsberechnung �ber Grund und der Gesch�tzwinkel zur Nordrichtung sind Ergebnis der  L�sung 2. geod. Haupaufgabe. 
l2,l1: longitude
b2,b1: lattitude


cos(e12)=sin(b1)*sin(b2) + cos(b1)*cos(b2)*cos(l2-l1)

d12=RE*e12

cos(x12) = (sin(b2) - sin(b1)*cos(e12)) / (cos(b1)*sin(e12))
sin(x12) = (sin(l2-l1)*cos(b2))/sin(e12)

azimuth=artan2(sin(x12) , cos(x12))

d12 ist die Entfernung des Ziels �ber der Erdoberfl�che
azimuth ist der Peilwinkel zur Nordrichtung

 
Der Elevationswinkel der Waffe wird �ber Anwendung des Kosinus und Sinussatzes ermittelt:
 
(RE+h) und RE sind Schenkel eines nicht rechtwinkligen Dreiecks. Der Winkel (e12) ist durch die L�sung der 2. geod. Haupaufgabe bereits bekannt.
 
�ber den Kosinus-Satz ergibt sich die Schussentfernung zwischen Schiff und Flugzeug.

schussentfernung^2=RE^2+(RE+h)^2-2*RE*(RE+h)*cos(e12)

(RE+h) und die Gescho�bahn sind ebenfalls 2 Schenkel eines nicht rechtwinkligen Dreiecks. Beide L�ngen sind bekannt. 
Zus�tzlich ist ein Winkel bekannt (e12 aus  der L�sung der 2. geod. Haupaufgabe). 

Damit l��t sich der Winkel zwischen RE und der Schussbahn mittels Sinus-Satz errechnen. 

sin(gamma) = (RE+h)*sin(e12) / schussentfernung 

Die Tangente an der Erdoberfl�che am Gesch�tz steht  senkrecht zu RE. 
Der errechnete Winkel gamma muss > 90�  sein, da sich das Ziel oberhalb der Erdoberfl�che befindet. 
Neben gamma ist auch 180�-gamma L�sung obiger Gleichung und nur in diesem Fall ist auch  gamma > 90�

Die Elevation ist der Winkel zwischen Tangete und Gesch�tzrohr, nicht zwischen Erdradius und Gesch�tzrohr (gamma ist der Hautwert): 

elevation = 180� - gamma - 90� = 90� - asin((RE+h)*sin(e12) / schussentfernung)

Die Erde wird als Kugel angenommen. Das Schiff hat die Altitude 0, da es sich immer auf Meeresh�he befinden mu�.

  

5) Command:
  
Als Gesch�tzbesatzung des Flugzeugtr�gers "USS Erich Gamma" m�chte ich, das die vom Feuerleitsystem berechneten Positionsparameter
zur Ansteuerung der Hydraulik  des Gesch�tzturms verwendet werden:

Die errechnete Elevation steuert den H�henrichtzylinder aufw�rts bzw. abw�rts, 
abh�ngig von der aktuellen Position der Waffe und den errechneten Positionparameter.
Das AZimuth steuert den Hydraulik-Motor rechts oder lins herum, 
abh�ngig von der aktuellen Position des Gesch�tzturms  und den errechneten Positionparameter

Gegeben:

-Valve als Interface und ValveMock als konkrete Implementierung f�r ein  Magnetventil.
-AngleSwitch und AngleSwitchMock f�r einen Schalter, der bei Erreichen eines Winkels den H�henrichtzylinder bzw. den Hydraulik-Motor abschaltet.
-Interface GunControl f�r die Implementierungen der einzelnen Kommandos
-Interface GunControlFactory zur Erstellung eines Makro-Kommandos
-Interface StatefullValve, erweitert Valve um Information, ob ein Ventil auf oder geschlossen ist.
-Priority Interface f�r die zeitliche Abfolge der Befehle 
-Swing-Gui zur Anzeige der Hydraulik-Befehle


Aufgabenstellung:

-Erstellen von StatefullValveImpl 
Um entscheiden zu k�nnen, welche Ventile wieder zuschlie�en sind, mu� der Zustand des Ventils bekannt sein.
Dazu ist f�r das Interface StatefullValve eine Implementierung StatefullValveImpl zu erstellen (Valve wird �ber Strategy gesetzt)
um das vom Hersteller zur Verf�gung gestellte Valve bzw. ValveMock um diese Funktionalit�t zu erg�nzen 


-Erstellen der einzelnen Kommandos (alle nutzen das Interface GunControl):
	TowerStopPositionControlImpl
	TowerOpenValveControlImpl
	ElevationStopPositionControlImpl
	ElevationOpenValveControlImpl
	CloseValveControlImpl
	
Das Commandpatern ist auch gedacht um Kommandos zusammenzufassen oder Statusinformationen hinzuf�gen.
TowerOpenValveControlImpl bzw. ElevationOpenValveControlImpl setzen   forwardStatefullValve und inverseStatefullValve per Strategy  und halten 
Informationen �ber den (aktuellen) Winkel (Azimuth bzw. Elevation) um entscheiden zu k�nnen, welches Ventil angesteuert werden mu�.

-Erstellen von GunControlMarcoImpl, als Liste aller Kommandos zur Hydraulik-Steuerung f�r die Positionierung des Gesch�tzturms auf ein Ziel.

Damit die Kommandos in der richtigen Reihenfolge erfolgen (Setzen der Enpositionen (azimuth, elevation) , 
�ffnen des Ventils (H�henrichtzylinder, Hydraulik-Motor), Schlie�en der Vertile kann man den Kommandos eine Priorit�t geben (Interface Priority) . 
Die Liste der Kommandoskann dann einfach nur entsprechend sortiert werden. 

-Erstellung von GunControlMarcoFactoryImpl (implements Interface GunControlFactory)


Anmerkung zur Bestimmung der Winkel der Waffe (H�henrichtzylinder und Drehwinkel): 
Der Winkel kann als rotierender Zeiger aufgefa�t werden. Nach der Euler'schen Formel ist dieser:

k*e�j(omega*t+x)=k*(cos(omega*t+x) - j*sin(omega*t+x))

Die Turmposition kann in ein elektr. Signal umgesetzt werden:
2 Spulen sind um 90� versetzt angeordnet. Parallel zur ersten Spule wird eine 3. Spule von einem cosf�rmigen Wechselstorm mit konst. Frequenz durchflossen.
Die 3. Spule wird proportional dem Turmwinkel zu den beiden anderen verdreht. 
Nach dem Induktionsgesetz  wird in den beiden anderen Spulen eine Spannung induziert.
Diese wird sich nach der eulerschen Formel auf die beiden Spulen verteilen (abh�ngig vom Winkel x). 
Mit entspechender Messtechnik kann z. B. der Scheitelwert der beiden Spannungen gemessen werden. 
(Drehfrequenz des Turms << Frequenz der Wechselspannung, Induktion durch die Drehbewegung kann vernachl�ssigt werden)

Im  Beispiel werden die entsprechenden Schwellwerte (als komplexe Amplituden) vorgegeben. 
Werden diese erreicht (Turm ist in die entsprechende Position gedreht), wird der �lkreislauf  abgeschaltet 
und die Waffe verbleibt in der entsprechenden Position. H�he und Drehwinkel der Waffe sind nach diesem Verfahren ermittelbar.
 


Decorator

Als  R�stungsfabrikant m�chte ich, nachdem die Gesch�ftsverbingung zu einem charakterlich degenerierten Monarchen weggebrochen ist,
mit einem faschistischen Diktator Gesch�fte machen. Dazu will ich diesen mit gepanzerten Kettenfahrzeugen beliefern, 
die mit bereits existierenden Waffen best�ckt (dekoriert) werden k�nnen.  
Dadurch steigere ich den Absatz f�r die bereits produzierten Kanonen und Maschinengewehre 
und verdiene zus�tzlich an den Kettenfahrzeugen auf Kosten fast der gesammten Welt.
Bricht das System erneut zusammen, schiebe ich es auf den Diktator, und versuche �hnliche Gesch�fte mit dem dann folgenden System zu machen.
Die Nachfrage ist durch die Weltmacht-Utopien des Diktators zumindest noch auf Jahre gesichert. 

Die Kettenfahrzeuge  werden mit folgenden Waffen erweitert:

Flugabwehrkanone 8,8 cm

MG 32 in Blende

MG 32 auf Turmluckenlaffete zur Flugabwehr.  

Hinweis:

1. Die Erweiterung des Fahrzeugs soll mittels Decorator Pattern erfolgen

gegeben:

VehicleImpl + ComponentsAware als Interface + AbstractComponent


a) zu erstellen ist VehicleDecoratorImpl

b) Es ist ein Builder zu erstellen, um die Erzeugung des Decorators zu kappseln und sicherer zu gestalten.

gegeben: VehicleBuilder (Interface) 

Zu erstellen ist VehicleBuilderImpl, das VehicleBuilder implementiert 


 

  

 