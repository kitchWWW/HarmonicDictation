%Hello
\header { tagline = "" } upper = \relative c'' { \clef treble \key
 e \major \time 4/4
 < e b gis >4 < e cis a >4 < fis cis a >4 < fis dis a >4 < e b gis >4 < fis cis a >4 < fis dis b >4 < b gis e >4 < a e cis >2 < b fis dis >2 < b gis e >1
} lower = \relative c { \clef bass \key
 e \major \time 4/4
 e4 cis4 a4 a4 gis4 fis4 b4 e4 cis,2^"OC!" b2 e1
 \bar "|."  } \score { \new PianoStaff << \set PianoStaff.instrumentName = #"  " \new Staff = "upper" \upper    \new Staff = "lower" \lower  >>  \layout { }  \midi { }}
