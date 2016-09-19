\version "2.18.2"\header{title ="Music from the Epoc"subtitle="for String Quartet"composer = "Brian Ellis"tagline =""}\score{\midi {}\layout{}\new StaffGroup<<\new Staff \with {  instrumentName = #"Violin 1"  shortInstrumentName = #"Vn.1"  midiInstrument = "Violin"}{		\relative c'' { \tempo 4 = 100 \key b \major \time 5/4
fis1 ~fis4 gis1 ~gis4 fis2. fis2 ais2 gis4 gis2 e1 ~e4 e2. dis4 dis4 dis1 e4 cis2 dis4 fis2 cis1 b4 b2. ais4 gis4 gis2. fis2 fis2. gis4 b4 b1 ~b4 
	}}\new Staff \with {  instrumentName = #"Violin 2"  shortInstrumentName = #"Vn.2"  midiInstrument = "Violin"}{	\relative c'' {  \key b \major	
dis1 ~dis4 e1 ~e4 cis2. dis2 fis2 dis4 e2 cis1 ~cis4 cis2. b4 ais4 b1 b4 ais2 b4 cis2 ais1 gis4 gis2. fis4 dis4 e2. cis2 dis2. e4 fis4 gis1 ~gis4 
	}}\new Staff \with {  instrumentName = #"Not brush "  shortInstrumentName = #"Vla. "  midiInstrument = "Viola"}{	\relative c' {	r1}	}\new Staff \with {  instrumentName = #"Viola "  shortInstrumentName = #"Vla. "  midiInstrument = "Viola"}{	\relative c' {	\clef "alto"	 \key b \major 
b1 ~b4 b1 ~b4 ais2. b2 cis2 b4 cis2 gis1 ~gis4 ais2. fis4 fis4 gis1 gis4 fis2 fis4 ais2 fis1 dis4 e2. cis4 b4 b2. ais2 b2. b4 dis4 e1 ~e4 
	}	}\new Staff \with {  instrumentName = #"Cello "  shortInstrumentName = #"Cel. "  midiInstrument = "Cello"}{	\relative c {	\clef "bass"	 \key b \major 
b1 ~b4 e1 ~e4 fis2. b2 fis2 gis4 cis2 cis1 ~cis4 ais2. b4 dis4 gis1 e4 fis2 b4 fis2 fis1 gis4 e2. fis4 gis4 e2. fis2 b2. e4 b4 e1 ~e4 
	}	}>>}
