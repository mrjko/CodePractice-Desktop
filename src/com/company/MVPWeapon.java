package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.company.Main.echo;


public class MVPWeapon {
    private HashMap<String, String> cardEffectDict = new HashMap<String, String>();
    private HashMap<String, String[]> mvpDict = new HashMap<String, String[]>();

    public MVPWeapon() {
        initCardDict(cardEffectDict);
        initMVPDict(mvpDict);
    }

    private void initCardDict(HashMap<String, String> hm) {
        hm.put("Brute", "Goblin");
        hm.put("Demi Human", "Hydra");
        hm.put("Insect", "Caramel");
        hm.put("Formless", "Pecopeco Egg");
        hm.put("Demon", "Strouf");
        hm.put("Plant", "Scorpion");

        hm.put("Fire", "Vadon");
        hm.put("Water", "Drainliar");
        hm.put("Wind", "Mandragora");
        hm.put("Earth", "Kaho");
        hm.put("Holy", "Orc Skeleton");
        hm.put("Shadow", "Santa Poring");
        hm.put("Poison", "Anacondaq");

        hm.put("Small", "Desert Wolf");
        hm.put("Medium", "Skel Worker");
        hm.put("Large", "Minorous");
    }

    private void initMVPDict(HashMap<String, String[]> hm) {
        hm.put("Eddga", new String[]{"Brute", "Fire", "Large"});
        hm.put("Osiris", new String[]{"Undead", "Undead", "Medium"});
        hm.put("Baphomet", new String[]{"Demon", "Dark", "Large"});
        hm.put("Doppelganger", new String[]{"Demon", "Dark", "Medium"});
        hm.put("Mistress", new String[]{"Insect", "Wind", "Small"});
        hm.put("Golden Thief Bug", new String[]{"Insect", "Fire", "Large"});
        hm.put("Orc Hero", new String[]{"Demi Human", "Earth", "Large"});
        hm.put("Drake", new String[]{"Undead", "Undead", "Medium"});
        hm.put("Maya", new String[]{"Insect", "Earth", "Large"});
        hm.put("Moonlight Flower", new String[]{"Demon", "Fire", "Medium"});
        hm.put("Pharaoh", new String[]{"Demi Human", "Dark", "Large"});
        hm.put("Phreenoi", new String[]{"Brute", "Neutral", "Large"});
        hm.put("Orc Lord", new String[]{"Demi Human", "Earth", "Large"});
        hm.put("Stormy Knight", new String[]{"Formless", "Wind", "Large"});
        hm.put("Hati", new String[]{"Brute", "Water", "Large"});
        hm.put("Dark Lord", new String[]{"Demon", "Undead", "Large"});
        hm.put("Turtle General", new String[]{"Brute", "Earth", "Large"});
        hm.put("Lord of the Dead", new String[]{"Demon", "Dark", "Large"});
        hm.put("Dracula", new String[]{"Demon", "Dark", "Large"});
        hm.put("Samurai Specter", new String[]{"Demi Human", "Dark", "Large"});
        hm.put("Tao Gunka", new String[]{"Demon", "Neutral", "Large"});
        hm.put("RSX", new String[]{"Formless", "Neutral", "Large"});
    }

    private String[] getMVPAttributes(String mvp) throws InvalidMVPException {
        String[] res = mvpDict.get(mvp);
        if (res == null) {
            throw new InvalidMVPException("Can't find the mvp!");
        }
        return res;
    }

    private List<String> getListOfCards(String mvp) throws InvalidMVPException {
        String[] bossAttributes = getMVPAttributes(mvp);
        return getAllRelatedCards(bossAttributes);
    }

    private List<String> getAllRelatedCards(String[] attrs) {
        List<String> res = new ArrayList<>();
        for (String attr : attrs) {
            String card = cardEffectDict.get(attr);
            if (card != null) {
                res.add(card);
            }
        }
        return res;
    }

    public HashMap<String, String[]> getListOfAllMVPs(String[] mvps) throws InvalidMVPException {
        HashMap<String, String[]> map = new HashMap<>();
        String[][] aggregateList = new String[mvps.length][mvps.length];
        int i = 0;
        for (String mvp : mvps) {
            List<String> list = getListOfCards(mvp);
            String[] stringArray = list.toArray(new String[0]);
            map.put(mvp, stringArray);
//            Iterator iter = list.iterator();
//            while(iter.hasNext()) {
//                String next = iter.next().toString();
//                if (map.get(next) != null) {
//                    map.put(next, map.get(next)+1);
//                } else {
//                    map.put(next, 1);
//                }
//            }
            i++;
        }

//        for (String key : map.keySet()) {
//            System.out.println(key + " Card: x" + map.get(key));
//        }
        return map;
    }
}
