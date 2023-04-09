import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ScoreFinder {


    public ScoreFinder(Player player, int joker) {
        //Arrayi sort etme
        sortPlayerTiles(player);
        System.out.println("Oyuncu Adı: " + player.getName());

        //Oyunculara taşlarını dağıt
        giveTilesToPlayers(player, joker);

        //Oyuncuların taşlarını incele
        checkPlayerTiles(player.getTiles(), player, joker);


        //Scoreları hesapla
        System.out.println("********* Oyuncunun elinde kalan taş sayısı " + findScore(player.getTiles(), player));
        System.out.println("---------------------------------------------------------");
    }

    private void giveTilesToPlayers(Player player, int joker) {

        if (player.getTiles()[0] != -1) {
            System.out.println(Arrays.toString(player.getTiles()) + "-> 15 elemanlı array");

        } else {
            //3 kişi için 14 lü dizi oluşturma
            byte[] copiedArray = new byte[player.getTiles().length - 1];
            System.arraycopy(player.getTiles(), 1, copiedArray, 0, player.getTiles().length - 1);
            player.setTiles(copiedArray);
            System.out.println(Arrays.toString(player.getTiles()) + " -> 14 elemanlı array");

        }
    }

    private byte[] sortPlayerTiles(Player player) {
        Arrays.sort(player.getTiles());
        return player.getTiles();
    }

    private byte checkPlayerTiles(byte[] array, Player player, int joker) {
        byte notPer = 0;
        byte differentColorPer = 0;
        byte jokerCount = 0;
        Set<Byte> difColor3Per = new HashSet<>();
        Set<Byte> difColor2Per = new HashSet<>();

        // Kontrol edilen indeksleri işaretleme
        boolean[] checked = new boolean[array.length];


        //Joker kontrolü
        for (byte b : array
        ) {
            if (b == (byte) joker) {
                jokerCount += 1;
            }
        }
        //Oyunucunun elindeki taşların değerlendirilmesi
        for (int i = 0; i < array.length; i++) {
            boolean foundSpecialGap = false;
            int specialGapCount = 0;
            for (int j = 0; j < array.length; j++) {
                if (i == j || checked[j]) {
                    // Kontrol edilmiş
                    continue;
                }
                //Farklı renkte olan aynı sayıların bulunup bulunmadığı kontrolü
                if (Math.abs(array[i] - array[j]) == 13 || Math.abs(array[i] - array[j]) == 26 || Math.abs(array[i] - array[j]) == 39 && array[i] < 52) {
                    foundSpecialGap = true;
                    specialGapCount += 1;
                    difColor2Per.add(array[j]);
                }
            }
            if (foundSpecialGap && specialGapCount > 2) {
                // İncelenen taştan farklı renkte aynı sayıda en az iki taş bulunduğunda differentColorPer artırılır
                differentColorPer++;
                difColor3Per.add(array[i]);
                notPer = (byte) (notPer - (differentColorPer * specialGapCount));
                for (int j = 0; j < array.length; j++) {
                    // Kontrol edilen indeksler işaretlenip tekrar farklı renk kontrolü yapılmaz
                    if (Math.abs(array[i] - array[j]) == 13 || Math.abs(array[i] - array[j]) == 26 || Math.abs(array[i] - array[j]) == 39) {
                        checked[j] = true;
                        difColor3Per.add(array[j]);
                    }
                }
            } else if (foundSpecialGap && specialGapCount == 2 && jokerCount > 0) {
                //will be sth
            }

            // İncelenen taşın bir serinin parçası olma kontrolü
            if (i < array.length - 1 && array[i] + 1 == array[i + 1]) {
                int j = i;
                while (j < array.length - 1 && array[j] + 1 == array[j + 1]) {
                    if (array[j] + 1 == joker || array[j + 1] == joker) {
                    }
                    //Kaç sayının ardışık olduğunu tespit eder
                    j++;
                }
                //2 tane ardışık sayı ile per var ise çalışır
                if ((j - i + 1) == 2) {
                    notPer += 1;
                    if (jokerCount > 0) {
                        System.out.println(array[i] + "-" + array[j] + " -> Okey in kullanılabileceği sıralı per");
                        player.setPer2((byte) (player.getPer2() + 1));

                    }
                } else {
                    System.out.print(array[i] + "-" + array[j]);
                    System.out.println(" (" + (j - i + 1) + ")" + " hazır per");
                    i = j;
                }

            }
            //Ardışık çift veya ek sayı var mı kontrolü
            else {
                if (i < array.length - 1 && array[i] == array[i + 1]) {
                    //Çift sayısını tut
                    player.setDoubleTiles((byte) (player.getDoubleTiles() + 1));
                    notPer += 1;
                } else if (i < array.length - 1 && jokerCount > 0 && array[i] + 2 == array[i + 1]) {
                    //aralarında 2 fark varsa
                    System.out.println(array[i] + "-" + array[i + 1] + " -> Okey in kullanılabileceği aralıklı per");
                    player.setPerWithSpace((byte) (player.getPerWithSpace() + 1));
                    notPer += 1;
                    //İncelenen taş ardışık çift/tek sayı iken 1 önceki taş ile 4 lü per olma kontrolü
                    if (i - 1 > 0 && array[i - 1] == array[i] - 1) {
                        if (i + 2 < array.length - 1 && array[i] + 2 == array[i + 2]) {
                            notPer -= 5;
                            player.setPer4((byte) (player.getPer4() + 1));
                            jokerCount--;
                            player.setJokerCount((byte) (player.getJokerCount() - 1));
                            System.out.println(array[i] + " -> 5 li per için okeyin koyulacağı yer");
                        } else {
                            notPer -= 4;
                            player.setPer4((byte) (player.getPer4() + 1));
                            jokerCount--;
                            player.setJokerCount((byte) (player.getJokerCount() - 1));
                            System.out.println(array[i] + " -> 4 lü per için okeyin koyulacağı yer");
                        }


                    }
                    //İncelenen taş ardışık çift/tek sayı iken 3 sonraki taş ile 4 lü per olma kontrolü
                    else if (i + 3 < array.length - 1 && array[i + 2] == array[i] + 3) {
                        notPer -= 4;
                        player.setPer4((byte) (player.getPer4() + 1));
                        jokerCount--;
                        player.setJokerCount((byte) (player.getJokerCount() - 1));
                        System.out.println(array[i + 1] + " -> 4 lü per için okeyin koyulacağı yer");
                    }
                } else {
                    notPer += 1;
                }
            }
        }
        //Değerlerin oyunuya aktarılması
        player.setDifColor2per((byte) difColor2Per.stream().count());
        player.setDifColor3per((byte) difColor3Per.stream().count());
        player.setJokerCount(jokerCount);
        player.setNonePer(notPer);
        System.out.println("-----");

        //Ekrana oyuncunun eli hakkında bilgilerin yazdırılması
        System.out.println(player.getJokerCount() > 0 ? jokerCount + " -> Okey bulunuyor, 2'li perlerden birine ekleyebilirsin" : "Okey bulunmuyor.");
        System.out.println(player.getPer2() > 0 ? player.getPer2() + " -> Sıralı 2 taşlı per sayısı" : "");
        System.out.println(player.getPerWithSpace() > 0 ? player.getPerWithSpace() + " -> Aralıklı 2 taşlı per sayısı" : "");
        System.out.println(player.getDifColor2per() > 0 && jokerCount > 0 ? player.getDifColor2per() + " -> Oyuncunun okeyle per yapabileceği farklı renkli aynı değerde taşlar" : "");
        System.out.println(!difColor2Per.isEmpty() && jokerCount > 0 ? difColor2Per : "");
        System.out.println(notPer + " -> Per olmayanlar");
        System.out.println(differentColorPer + " -> Farklı Renkli Per Sayısı");
        System.out.println(player.getDoubleTiles() + " -> Cift Sayısı");
        System.out.println("------");
        System.out.println(!difColor3Per.isEmpty() ? difColor3Per + "3 lü per olan farklı renk taşlar" : "");

        return 0;
    }

    private byte findScore(byte[] array, Player player) {
        //Okey yoksa
        if (player.getJokerCount() == 0) {
            byte doublesScore = (byte) (player.getTiles().length - (player.getDoubleTiles() * 2));
            byte normalScore = (byte) (player.getNonePer());
            if (doublesScore < normalScore)
                player.setScore(doublesScore);
            else {
                player.setScore(normalScore);
            }
        }
        //1 Okey Varsa
        if (player.getJokerCount() == 1) {
            byte doublesScore = (byte) (player.getTiles().length - (player.getDoubleTiles() * 2));
            byte normalScore = (byte) (player.getNonePer());

            if ((player.getPer2() > 0 || player.getPerWithSpace() > 0 || player.getDifColor2per() > 0) && player.getPer4() < 1) {
                normalScore -= 3;
            } else {
                if (player.getDoubleTiles() < 7) {
                    doublesScore -= 2;
                }
            }
            if (doublesScore < normalScore) {
                player.setScore(doublesScore);
            } else {
                player.setScore(normalScore);
            }

        }

        //2 okey varsa
        if (player.getJokerCount() == 2) {
            byte doublesScore = (byte) (player.getTiles().length - (player.getDoubleTiles() * 2));
            byte normalScore = (byte) (player.getNonePer());

            if ((player.getPer2() + player.getPerWithSpace()) > 1 || (player.getDifColor2per() + player.getPer2()) > 1 ||
                    (player.getDifColor2per() + player.getPerWithSpace()) > 1 && player.getPer4() < 1) {
                normalScore -= 6;
            } else {
                if (player.getDoubleTiles() < 6) {
                    doublesScore -= 4;
                }
            }
            if (doublesScore < normalScore) {
                player.setScore(doublesScore);
            } else {
                player.setScore(normalScore);
            }

        }


        return player.getScore();
    }


}
