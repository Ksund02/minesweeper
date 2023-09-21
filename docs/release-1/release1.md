# Release 1: Innledende støtte og funksjonalitet
I denne første utgivelsen får brukeren muligheten til å starte Minesweeper-spillet. Skjermen viser spillets grensesnitt, og det er mulig å klikke på ruter for å se om ruten er en bombe eller ikke, men utenom dette, er det foreløpig begrenset med spillfunksjonalitet.

## Kjernefunksjonalitet og Utviklingsstatus
Det meste av spillets logikk er allerede på plass, men brukergrensesnittet er ikke fullstendig implementert ennå. Dette vil bli introdusert i en fremtidig release.
Lagring og innlesing fra fil er implementert for spillets highscore-liste. Selv om denne funksjonen fungerer, vil den sannsynligvis endres ettersom spillogikken blir utviklet videre. På dette stadiet kan man for eksempel ikke legge inn nye navn i highscore-listen, siden man ikke får spilt spillet.

## Dokumentasjon
Vi har laget README-filer i både rotnivåmappen og i Minesweeper-mappen. Disse filene gir en kort oversikt over prosjektet og instruksjoner for hvordan man kjører programmet.
For å forbedre brukeropplevelsen har vi også lagt til emojis i README-filene.
Det finnes en lenke til en brukerhistorie fra personasen “Truls” i README. Denne har tjent som inspirasjon for mange "issues" i vårt GitLab-repository.


## Testing
Enkle tester er utviklet for å bekrefte at en tekststreng blir skrevet ut til terminalen når en knapp blir trykket på. Selv om testen i seg selv ikke gir mye verdi, bekrefter den at test-oppsette fungerer som forventet.
Vi genererer også en Jacoco-rapport hver gang testene kjøres, noe som gir oss mulighet til å vurdere testdekningen. For vår nåværende, enkle kontroller, er testdekningen 100%.
