Gegeben:

- Interface ArtistState


- Interface Checker, dessen Implentierungen die Statusübergänge vom aktuellen in den FolgeStatus ermittelt

- RandomCheckerImpl als Implementierung von Checker 
  
  RandomChecker wählt zufälling nach Wahrscheinlichkeiten, z.B.  50%  CheckResult.Stay (Isaac Newton: Trägheitsgesetz) 25% CheckResult.Success 25% CheckResult.Failed
  
  
 - Main-Klasse StupidGirls um den LifeCycle simmulieren zu können 
   und die Anzahl der durchlaufenen Stati, den EndStatus und das Einkommen auszugeben.
   
 - AbstarctArtistState (als Grundlage für die StatusImplemntierungen)


AufgabenStellung:

Diesmal wird der Lifecycle des Artist-Girls durch ArtistState-Klassen modelliert, die ihren Nachfolgestatus mittes Dependency-Injection 
in der Beankonfiguration gesetzt bekommen. Der Checker wird ebenfalls per Dependencyinjection in die ArtistState Klassen gesetzt.  Es sollen  bei den ArtistStates
zwei Klassen unterschieden werden, solche die einen Endzustand darstellen (Ammount=0, kein Folgezustand, kein FailedZustand, kein Checker) und solche, die Übergänge im Success und Failed-Fall
beinhalten. Die Failedzustände können nicht (zumindest nicht alle) direkt mittels Dependency-Injektion  gesetzt werden, da dies zu zyklischen Abhängigkeiten führen würde 
und Spring mit einer entsprechenden Fehlermeldung aussteigen würde. Die Failed-Zustande werden mittels eines BeanFactoryPostProcessor (Interface muß implentiert werden) 
gesetzt. Wird  BeanFactoryPostProcessor   in das xml file mit den Beandefinitionen eingetragen, wird er durchlaufen, wenn die Beanfactory erzeugt wurde. 
Auf diese Weise kann die Beanfactory nachbearbeitet werden 

DependencyInjection wird mittels Spring realisiert. Die Beandefinition erfolgt klass. in einem xml-file. 

Implementiert werden sollen:

FinalArtistStateImpl , NonFinalArtistStateImpl, beans.xml , FailedStateBeanFactoryPostProcessor
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


Start StupidGirls main oder Commandline mvn spring-boot:run