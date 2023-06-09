public class Player {

    private String name;
    private byte jokerCount = 0;
    private byte doubleTiles = 0;
    private byte per2 = 0;
    private byte per4 = 0;
    private byte nonePer = 0;
    private byte score = 0;
    private byte[] tiles = new byte[15];
    private byte difColor2per = 0;
    private byte difColor3per = 0;
    private byte perWithSpace = 0;

    private boolean used4 = false;

    public Player(String name) {
        setName(name);
    }

    public void setPer4(byte per4) {
        this.per4 = per4;
    }

    public void setNonePer(byte nonePer) {
        this.nonePer = nonePer;
    }

    public void setScore(byte score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getPer2() {
        return per2;
    }

    public void setPer2(byte per2) {
        this.per2 = per2;
    }

    public byte getPer4() {
        return per4;
    }

    public byte[] getTiles() {
        return tiles;
    }

    public void setTiles(byte[] tiles) {
        this.tiles = tiles;
    }

    public byte getScore() {
        return score;
    }

    public byte getNonePer() {
        return nonePer;
    }

    public byte getDoubleTiles() {
        return doubleTiles;
    }

    public void setDoubleTiles(byte doubleTiles) {
        this.doubleTiles = doubleTiles;
    }

    public byte getDifColor2per() {
        return difColor2per;
    }

    public void setDifColor2per(byte difColor2per) {
        this.difColor2per = difColor2per;
    }

    public byte getDifColor3per() {
        return difColor3per;
    }

    public void setDifColor3per(byte difColor3per) {
        this.difColor3per = difColor3per;
    }

    public byte getJokerCount() {
        return jokerCount;
    }

    public void setJokerCount(byte jokerCount) {
        this.jokerCount = jokerCount;
    }

    public byte getPerWithSpace() {
        return perWithSpace;
    }

    public void setPerWithSpace(byte perWithSpace) {
        this.perWithSpace = perWithSpace;
    }


}
