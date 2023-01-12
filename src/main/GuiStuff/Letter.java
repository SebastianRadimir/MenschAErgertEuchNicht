package GuiStuff;

import javax.swing.*;

/** an enum for every letter in scrabble,its points and its image */
public enum Letter {

    a('A',1, new ImageIcon("src/images/characters/A.png")),
    b('B',3, new ImageIcon("src/images/characters/B.png")),
    c('C',3, new ImageIcon("src/images/characters/C.png")),
    d('D',2, new ImageIcon("src/images/characters/D.png")),
    e('E',1, new ImageIcon("src/images/characters/E.png")),
    f('F',4, new ImageIcon("src/images/characters/F.png")),
    g('G',2, new ImageIcon("src/images/characters/G.png")),
    h('H',4, new ImageIcon("src/images/characters/H.png")),
    i('I',1, new ImageIcon("src/images/characters/I.png")),
    j('J',8, new ImageIcon("src/images/characters/J.png")),
    k('K',5, new ImageIcon("src/images/characters/K.png")),
    l('L',1, new ImageIcon("src/images/characters/L.png")),
    m('M',3, new ImageIcon("src/images/characters/M.png")),
    n('N',1, new ImageIcon("src/images/characters/N.png")),
    o('O',1, new ImageIcon("src/images/characters/O.png")),
    p('P',3, new ImageIcon("src/images/characters/P.png")),
    q('Q',10, new ImageIcon("src/images/characters/Q.png")),
    r('R',1, new ImageIcon("src/images/characters/R.png")),
    s('S',1, new ImageIcon("src/images/characters/S.png")),
    t('T',1, new ImageIcon("src/images/characters/T.png")),
    u('U',1, new ImageIcon("src/images/characters/U.png")),
    v('V',4, new ImageIcon("src/images/characters/V.png")),
    w('W',4, new ImageIcon("src/images/characters/W.png")),
    x('X',8, new ImageIcon("src/images/characters/X.png")),
    y('Y',4, new ImageIcon("src/images/characters/Y.png")),
    z('Z',10, new ImageIcon("src/images/characters/Z.png")),
    blank('?',0, new ImageIcon("src/images/characters/BLANK.png"));

    private char letter;
    private ImageIcon icon;
    private int points;

    Letter(char letter,int points,ImageIcon icon){
        this.icon = icon;
        this.letter = letter;
        this.points = points;
    }
    public void setLetter(char newLetter){
        this.letter = newLetter;
    }

    public char getLetter(){return this.letter;}
    public int getPoints(){return this.points;}
    public ImageIcon getIcon(){return this.icon;}

    /** sets a blanc/joker to another letter image with 0 points*/
    public void setBlank(Letter setLetter){
        if (this == Letter.blank && setLetter != null){
            this.letter = setLetter.getLetter();
            this.icon = setLetter.getIcon();
            this.points = 0;
        }
    }
    /**returns a Letter enum from the given char*/
    public static Letter charToLetter(char ch){
        for (Letter l:Letter.values()) {
            if (l.getLetter() == ch){
                return l;
            }
        }
        return null;
    }

}
