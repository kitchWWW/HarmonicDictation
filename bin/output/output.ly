\version "2.18.2"\header{title ="Music from the Epoc"subtitle="for String Quartet"composer = "Brian Ellis"tagline =""}\score{\midi {}\layout{}\new StaffGroup<<\new Staff \with {  instrumentName = #"Violin 1"  shortInstrumentName = #"Vn.1"  midiInstrument = "Violin"}{		\relative c'' { \tempo 4 = 100 \key g \major \time 5/4
d1 ~d4 e2. e2 c2. d4 a4 b1 ~b4 b2. c2 c2 c4 a2 a1 ~a4 g2. g2 fis2. fis4 fis4 fis2 d4 b2 c1 ~c4 a1 b4 d2. d2 
	}}\new Staff \with {  instrumentName = #"Violin 2"  shortInstrumentName = #"Vn.2"  midiInstrument = "Violin"}{	\relative c'' {  \key g \major	
b1 ~b4 b2. c2 a2. a4 fis4 g1 ~g4 g2. g2 a2 a4 fis2 fis1 ~fis4 e2. e2 d2. c4 d4 c2 b4 g2 g1 ~g4 fis1 g4 a2. b2 
	}}\new Staff \with {  instrumentName = #"Not brush "  shortInstrumentName = #"Vla. "  midiInstrument = "Viola"}{	\relative c' {	r1}	}\new Staff \with {  instrumentName = #"Viola "  shortInstrumentName = #"Vla. "  midiInstrument = "Viola"}{	\relative c' {	\clef "alto"	 \key g \major 
g1 ~g4 g2. a2 e2. fis4 d4 d1 ~d4 e2. e2 e2 fis4 c2 d1 ~d4 b2. c2 a2. a4 a4 a2 g4 d2 e1 ~e4 d1 d4 fis2. g2 
	}	}\new Staff \with {  instrumentName = #"Cello "  shortInstrumentName = #"Cel. "  midiInstrument = "Cello"}{	\relative c {	\clef "bass"	 \key g \major 
g1 ~g4 e2. a2 a2. d4 d4 g1 ~g4 e2. c2 a2 fis4 fis2 d1 ~d4 e2. c2 d2. fis4 d4 fis2 g4 g2 c1 ~c4 d1 g4 d2. g2 
	}	}>>}
