header = { title = \"Harmonic Dictation\" composer = \"Brian Ellis\" tagline = \"\" } upper = \\relative c'' { \\clef treble \\key

\\major \\time 4/4

  a4 b c d
} lower = \\relative c { \\clef bass \\key

\\major \\time 4/4

  a2 c
} \\score { \\new PianoStaff << \\set PianoStaff.instrumentName = #\"Piano  \" \\new Staff = \"upper\" \\upper    \\new Staff = \"lower\" \\lower  >>  \\layout { }  \\midi { }}