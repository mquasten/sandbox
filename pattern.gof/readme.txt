1) Proxy 
Als Sarah Palin möchte ich verhindern das sich gleichgeschlechtliche Personen miteinander vernügen.

Gegeben: 
-Interface Kissable 
-abstracte Klasse Person, die Kissable implementiert 
-Klassen Boy and Girl, die von Person erben
-PersonService als Interface und BasicPersonServiceImpl, die Kissable Instanzen für Boy und Girl erzeugen


Aufgabenstellung:


1.1) DynamicKissProxyImpl erstellen: Bei Aufruf von "Kiss public final String kiss(final Kissable kissed)"
 wird eine Exception geworfen,wenn: kissed.type() == this.type , sonst wird an die kiss Methode des Objektes delegiert.

1.2) DynamicProxyPersonServiceImpl: Dynamic-Proxies für Girl und Boys erstellen, 
Service erbt von BasicPersonServiceImpl: überschreiben der Methode: "protected Kissable proxy(final Kissable person)".


3) Das gleiche für CGLIB-Proxies: Proxy und CGLIBProxyPersonServiceImpl erstellen



2) Strategy

Als Anwender einer math. Funktionsbibliotek  möchte ich bestimmte Integrale numerisch lösen 
und dabei unterschiedliche (auch selbst implementierte) Algorithmen (Simpson-, Trapez-Regel usw.) verwenden können.


Gegeben:
- ServiceInterface "double area(double min, double max, int n)" zur Integration reeller Funktionen. 
- Interface RealFunction  double result(double value) zur Berechnung des Funktionswertes einer reelen Funktion,
- Implementierungen für RealFunktion: Parabel und BshRealFunctionImpl 


Trapezformel:

Streifenbreite h= (b-a)/n 
Stützstellen xk= a+k*h (k=0,1,...,n) 
Stützwerte: y0, y1,...,y2n

s1= y0 + yn , s2=y1+y2+...+yn-1

integral=(1/2*s1+s2)*h


Simsonsche Formel:

Streifenbreite h= (b-a)/n 
Stützstellen xk= a+k*h (k=0,1,...,2*n) 
Stützwerte: y0, y1,...,yn

Streifenbreite h= (b-a)/(2*n)

s0=y0+y2n
s1=y1+y3+...+y2n-1
s2=y2+y4+...+y2n-2 

integral=/s0+4*s1+2*s2)*h/3


Fehlerabschätzung (2*n is durch 4 teilbar):

deltaI=1/15*(integral(h) - integral(2*h))

integral =integral(h) + daltaI


Aufgabenstellung:

2.1 Zu Implementieren sind SimpsonIntegrationServiceImpl und TrapezIntegrationServiceImpl (für Simpson bzw. Trapez-Regel).
Beide verwenden das Interface IntegrationService

2.2. Es ist ein universeller Mechanismus zur Erstellung von Java-Beans 
und das automatische Setzen der Abhängigkeiten zu erstellen : SimpleBeanFactoryImpl 
SimpleBeanFactoryImpl implemtiert das Interface BeanFactory.


3 State

Als MusikProduzent möchte ich, das Pop-Sängerinnen einem bestimmten vorgefertigten Verhalten, abhängig von 
ihrem Karrie-Status entsprechen, um sie mit vorgefertigten Maketing-Strategien vermarkten zu können und
möglichst leicht Geld an ihnen zu verdienen.


Gegeben:

- Interface ArtistState

- Enum ArtistStateFactory, für alle möglichen Stati wird eine Status-Klasse erzeugt.

- Interface Checker, dessen Implentierungen die Statusübergänge vom aktuellen in den FolgeStatus ermittelt

- RandomCheckerImpl als Implementierung von Checker 
  und InnerClass für StayOnStateChecker als konkrete Implementierungen von Checker
  RandomChecker wählt zufälling nach Wahrscheinlichkeiten:  50%  CheckResult.Stay 25% CheckResult.Success 25% CheckResult.Failed,
  StayOnStateChecker liefert immer CheckResult.Stay (wird für die finalen Zustände verwendet)
  
 - Main-Klasse StupidGirls um den LifeCycle simmulieren zu können 
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


4 + 5 )  Als CO des Flugzeugträgers "USS Erich Gamma"  möchte ich , das  für die vom Tracking Radar erfaßten Flugzeuge Feuerleitlösungen errechnet werden,
und die Geschütze automatisch auf die Ziele ausgerichtet werden.


4) Observer:

Als Feuerleitoffizier des Flugzeugträgers "USS Erich Gamma" möchte ich, 
das die vom Tracking-Radar erfaßten Koordinaten  der Flugzeuge automatisch  zur Errechnung einer Feuerleitlösung (Positionsparameter des Geschützturms 
für die Bekämpfung der entsprechenden Ziels) führen. Unter Koordinaten sind Lattitude, Longitude und Altitude des Ziels zu verstehen.
Als Positionsparameter für das Feuerleitsystem werden der Winkel des Waffenturms zur Nordrichtung, 
die Elevaltion des Geschützrohres (bei gerader Flugbahn des Geschosses), die Entfernung des Zieles über Grund vom Schiff 
und die direkte Entfernung des Schiffs zum Ziel benötigt.
Ziele mit einer Entfernung > 100 km   sind für das Waffensystem mangels Reichweite nicht von Interesse. 

Gegeben:

-Angle Klasse zur Representaion von Winkeln  
-Location Interface für Koordinaten (Longitude, Lattitude und Altitude, LocationImpl als konkrete Implementirung des Interfaces
-Aircraft Interface zur Beschreibung eines Flugzeugs und seiner Koordinaten
-Mig29 als konkrete Implementierung von Aircraft
-TargetAware Interface zur Übergabe von Flugzeugen und den entsprechenden PositionsParametern an die Waffe
-Interface Subject für ObserverSubject
-Interface Observer für Observer 
-Interface PositionCalculator zur Berechnung der Positionsparameter der Waffe, PositionCalculatorImpl als konkrete Implementierung
-SwingGui zur Simmulation von Radar und Waffe

Es wird nicht die ObserverImplemntierung von Java verwendet (da sie Unsauber ist), darum die eigenen Interfaces für Subject und Observer

AufgabenStellung:
Realisierung des Observermusters: Vom Radar getracktes Flugzeug  (AircraftSubjectImpl) ist ObserverSubject,
das Waffensysten (AircraftObserverImpl) ist Observer
- AircraftSubjectImpl erstellen: implements Subject und  Aircraft. 
  Die Koordinaten werden von einer über Strategy austauschbaren konkreten Implementierung (Mig29) geliefert.
  Die Berechnung der Positionsparameter erfolgt über eine per Strategie austauschbare Implementierung (PositionsparameterImpl)
  Equals und HashCode sind zu implemtieren.
- AircraftObserver erstellen: implements Observer und TargetsAware.
  Als Abweichung zum Observer der GoF können einem Observer n Subjects (Flugzeuge, Ziele) zugeordnet sein. 
  Die Bekämpfung erfolgt dann entweder durch unterschiedliche Geschütze oder zeitlich nacheinander, was nicht von Relevanz ist)
  Die Zuordnung der Subjects zu den errechneten Positionsparametern  wird als private final Map<Aircraft, Position> positions abgebildet und
  über TargetAware vom Feuerleitsystem gelesen und weiterverarbeitet.
  
 
Anmerkung zur Bestimmung der Positionsparameter der Waffe: 
Die Entfernungsberechnung über Grund und der Geschützwinkel zur Nordrichtung sind Ergebnis der  Lösung 2. geod. Haupaufgabe. 
l2,l1: longitude
b2,b1: lattitude


cos(e12)=sin(b1)*sin(b2) + cos(b1)*cos(b2)*cos(l2-l1)

d12=RE*e12

cos(x12) = (sin(b2) - sin(b1)*cos(e12)) / (cos(b1)*sin(e12))
sin(x12) = (sin(l2-l1)*cos(b2))/sin(e12)

azimuth=artan2(sin(x12) , cos(x12))

d12 ist die Entfernung des Ziels über der Erdoberfläche
azimuth ist der Peilwinkel zur Nordrichtung

 
Der Elevationswinkel der Waffe wird über Anwendung des Kosinus und Sinussatzes ermittelt:
 
(RE+h) und RE sind Schenkel eines nicht rechtwinkligen Dreiecks. Der Winkel (e12) ist durch die Lösung der 2. geod. Haupaufgabe bereits bekannt.
 
Über den Kosinus-Satz ergibt sich die Schussentfernung zwischen Schiff und Flugzeug.

schussentfernung^2=RE^2+(RE+h)^2-2*RE*(RE+h)*cos(e12)

(RE+h) und die Geschoßbahn sind ebenfalls 2 Schenkel eines nicht rechtwinkligen Dreiecks. Beide Längen sind bekannt. 
Zusätzlich ist ein Winkel bekannt (e12 aus  der Lösung der 2. geod. Haupaufgabe). 

Damit läßt sich der Winkel zwischen RE und der Schussbahn mittels Sinus-Satz errechnen. 

sin(gamma) = (RE+h)*sin(e12) / schussentfernung 

Die Tangente an der Erdoberfläche am Geschütz steht  senkrecht zu RE. 
Der errechnete Winkel gamma muss > 90°  sein, da sich das Ziel oberhalb der Erdoberfläche befindet. 
Neben gamma ist auch 180°-gamma Lösung obiger Gleichung und nur in diesem Fall ist auch  gamma > 90°

Die Elevation ist der Winkel zwischen Tangete und Geschützrohr, nicht zwischen Erdradius und Geschützrohr (gamma ist der Hautwert): 

elevation = 180° - gamma - 90° = 90° - asin((RE+h)*sin(e12) / schussentfernung)

Die Erde wird als Kugel angenommen. Das Schiff hat die Altitude 0, da es sich immer auf Meereshöhe befinden muß.

  

5) Command:
  
Als Geschützbesatzung des Flugzeugträgers "USS Erich Gamma" möchte ich, das die vom Feuerleitsystem berechneten Positionsparameter
zur Ansteuerung der Hydraulik  des Geschützturms verwendet werden:

Die errechnete Elevation steuert den Höhenrichtzylinder aufwärts bzw. abwärts, 
abhängig von der aktuellen Position der Waffe und den errechneten Positionparameter.
Das AZimuth steuert den Hydraulik-Motor rechts oder lins herum, 
abhängig von der aktuellen Position des Geschützturms  und den errechneten Positionparameter

Gegeben:

-Valve als Interface und ValveMock als konkrete Implementierung für ein  Magnetventil.
-AngleSwitch und AngleSwitchMock für einen Schalter, der bei Erreichen eines Winkels den Höhenrichtzylinder bzw. den Hydraulik-Motor abschaltet.
-Interface GunControl für die Implementierungen der einzelnen Kommandos
-Interface GunControlFactory zur Erstellung eines Makro-Kommandos
-Interface StatefullValve, erweitert Valve um Information, ob ein Ventil auf oder geschlossen ist.
-Priority Interface für die zeitliche Abfolge der Befehle 
-Swing-Gui zur Anzeige der Hydraulik-Befehle


Aufgabenstellung:

-Erstellen von StatefullValveImpl 
Um entscheiden zu können, welche Ventile wieder zuschließen sind, muß der Zustand des Ventils bekannt sein.
Dazu ist für das Interface StatefullValve eine Implementierung StatefullValveImpl zu erstellen (Valve wird über Strategy gesetzt)
um das vom Hersteller zur Verfügung gestellte Valve bzw. ValveMock um diese Funktionalität zu ergänzen 


-Erstellen der einzelnen Kommandos (alle nutzen das Interface GunControl):
	TowerStopPositionControlImpl
	TowerOpenValveControlImpl
	ElevationStopPositionControlImpl
	ElevationOpenValveControlImpl
	CloseValveControlImpl
	
Das Commandpatern ist auch gedacht um Kommandos zusammenzufassen oder Statusinformationen hinzufügen.
TowerOpenValveControlImpl bzw. ElevationOpenValveControlImpl setzen   forwardStatefullValve und inverseStatefullValve per Strategy  und halten 
Informationen über den (aktuellen) Winkel (Azimuth bzw. Elevation) um entscheiden zu können, welches Ventil angesteuert werden muß.

-Erstellen von GunControlMarcoImpl, als Liste aller Kommandos zur Hydraulik-Steuerung für die Positionierung des Geschützturms auf ein Ziel.

Damit die Kommandos in der richtigen Reihenfolge erfolgen (Setzen der Enpositionen (azimuth, elevation) , 
Öffnen des Ventils (Höhenrichtzylinder, Hydraulik-Motor), Schließen der Vertile kann man den Kommandos eine Priorität geben (Interface Priority) . 
Die Liste der Kommandoskann dann einfach nur entsprechend sortiert werden. 

-Erstellung von GunControlMarcoFactoryImpl (implements Interface GunControlFactory)


Anmerkung zur Bestimmung der Winkel der Waffe (Höhenrichtzylinder und Drehwinkel): 
Der Winkel kann als rotierender Zeiger aufgefaßt werden. Nach der Euler'schen Formel ist dieser:

k*e°j(omega*t+x)=k*(cos(omega*t+x) - j*sin(omega*t+x))

Die Turmposition kann in ein elektr. Signal umgesetzt werden:
2 Spulen sind um 90° versetzt angeordnet. Parallel zur ersten Spule wird eine 3. Spule von einem cosförmigen Wechselstorm mit konst. Frequenz durchflossen.
Die 3. Spule wird proportional dem Turmwinkel zu den beiden anderen verdreht. 
Nach dem Induktionsgesetz  wird in den beiden anderen Spulen eine Spannung induziert.
Diese wird sich nach der eulerschen Formel auf die beiden Spulen verteilen (abhängig vom Winkel x). 
Mit entspechender Messtechnik kann z. B. der Scheitelwert der beiden Spannungen gemessen werden. 
(Drehfrequenz des Turms << Frequenz der Wechselspannung, Induktion durch die Drehbewegung kann vernachlässigt werden)

Im  Beispiel werden die entsprechenden Schwellwerte (als komplexe Amplituden) vorgegeben. 
Werden diese erreicht (Turm ist in die entsprechende Position gedreht), wird der Ölkreislauf  abgeschaltet 
und die Waffe verbleibt in der entsprechenden Position. Höhe und Drehwinkel der Waffe sind nach diesem Verfahren ermittelbar.
 


Decorator

Als  Rüstungsfabrikant möchte ich, nachdem die Geschäftsverbingung zu einem charakterlich degenerierten Monarchen weggebrochen ist,
mit einem faschistischen Diktator Geschäfte machen. Dazu will ich diesen mit gepanzerten Kettenfahrzeugen beliefern, 
die mit bereits existierenden Waffen bestückt (dekoriert) werden können.  
Dadurch steigere ich den Absatz für die bereits produzierten Kanonen und Maschinengewehre 
und verdiene zusätzlich an den Kettenfahrzeugen auf Kosten fast der gesammten Welt.
Bricht das System erneut zusammen, schiebe ich es auf den Diktator, und versuche ähnliche Geschäfte mit dem dann folgenden System zu machen.
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


 

  

 