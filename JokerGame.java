import java.util.*;

public class JokerGame {

    private static byte[] allTiles = new byte[106];

    private static Player[] players = new Player[4];
    private static int jokerTile ;
    private static int fakeJoker ;
    private static Random random = new Random();

    public static void main(String[] args) {

        //Oyunu başlatma yazısı
        System.out.println("\n###################################################################################");
        System.out.println("#     #    #     #####     #    #     #    ######  #######  #####     #    #     # ");
        System.out.println("#     #   # #   #     #   # #   ##    #    #     # #     # #     #   # #   ##    # ");
        System.out.println("#     #  #   #  #        #   #  # #   #    #     # #     # #        #   #  # #   # ");
        System.out.println("####### #     #  #####  #     # #  #  #    #     # #     # #  #### #     # #  #  # ");
        System.out.println("#     # #######       # ####### #   # #    #     # #     # #     # ####### #   # # ");
        System.out.println("#     # #     # #     # #     # #    ##    #     # #     # #     # #     # #    ## ");
        System.out.println("#     # #     #  #####  #     # #     #    ######  #######  #####  #     # #     # ");
        System.out.println("\n###################################################################################");
        System.out.println("\n\n\n\t\t   ########### Okey Oyunuma hoş geldiniz!  ##################     \n\n");


        //Taşların Değerini Oluştur
        for (byte i = 0; i <53 ; i++) {
            allTiles[i] = i;
            allTiles[i+53] = i;
        }

        //Taşları Karıştır
        randomiseTiles(allTiles);

        //Okey Belirle
        defineJoker(allTiles);

        //Oyuncuları Tanımla
        Player player1 = new Player("A");
        Player player2 = new Player("B");
        Player player3 = new Player("C");
        Player player4 = new Player("D");

        //1 kişiye 15 diğer 3 kişiye 14 taş dağıt
        dealPlayerTiles(Arrays.asList(player1,player2,player3,player4),allTiles);

        //Oyuncu taşlarının değerlendirilmesi
        ScoreFinder scoreFinder1 = new ScoreFinder(player1,jokerTile);
        players[0]=player1;
        ScoreFinder scoreFinder2 = new ScoreFinder(player2,jokerTile);
        players[1]=player2;
        ScoreFinder scoreFinder3 = new ScoreFinder(player3,jokerTile);
        players[2]=player3;
        ScoreFinder scoreFinder4 = new ScoreFinder(player4,jokerTile);
        players[3]=player4;

        //Kazanan oyuncunun belirlenmesi
        System.out.println("Kazanan Oyuncu/Oyuncular");
        winner(players).stream().map(Player::getName).forEach(System.out::println);
    }


    private static List<Player> winner(Player[] players) {
        List<Player> winners = new ArrayList<>();
        //Minimum score lu ouncuyu bulma
        byte score = players[0].getScore();
        for (int i = 0; i < players.length; i++) {
            if (score > players[i].getScore()) {
                winners.clear();
                winners.add(players[i]);
            }
            else if (score == players[i].getScore()) {
                winners.add(players[i]);
            }
        }

        return winners;
    }

    private static void dealPlayerTiles(List<Player> list,byte[] tiles) {
        //Random bir player a 15 taş verme
        int randomPlayer = random.nextInt(4)+1;
        List<Player> players = new ArrayList<>(list);
        for (int i = 0; i < 15; i++) {
            players.get(randomPlayer-1).getTiles()[i]=allTiles[i];
        }
        players.remove(players.get(randomPlayer-1));
        for(int i = 15;i < 29; i++) {
            players.get(0).getTiles()[i-15] = allTiles[i];
            players.get(1).getTiles()[i-15] = allTiles[i+14];
            players.get(2).getTiles()[i-15] = allTiles[i+28];
        }
        //14 elemanlı arraylerin son elemanını -1 yapıyoruz böylece sort edilince 14 eleman olduğu anlaşılacak
        players.get(0).getTiles()[14] = -1;
        players.get(1).getTiles()[14] = -1;
        players.get(2).getTiles()[14] = -1;

    }

    private static void randomiseTiles(byte[] tilesArray){

        List<Byte> list = new ArrayList<>();
        for (byte b : allTiles) {
            list.add(b);
        }

        Collections.shuffle(list);

        // Karıştırılmış elemanları array e geri aktarım
        int i = 0;
        for (Byte b : list) {
            allTiles[i++] = b;
        }
    }

    private static void defineJoker(byte[] array){
        //Okey belirle
        int randomNumber = random.nextInt(allTiles.length-1);
        fakeJoker = allTiles[randomNumber];

        switch (fakeJoker) {
            case 12 -> jokerTile = 0;
            case 25 -> jokerTile = 13;
            case 38 -> jokerTile = 26;
            case 51 -> jokerTile = 39;
            //Sahte okeyin gösterge olması konrolü
            case 52 -> defineJoker(allTiles);
            default -> jokerTile = fakeJoker + 1;
        }
        System.out.println("************************************************");
        System.out.println("Sahte Okey -> "+fakeJoker);
        System.out.println("Okey -> "+jokerTile);
        System.out.println("************************************************");

    }
}
