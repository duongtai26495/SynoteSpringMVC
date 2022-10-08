package com.duongtai.syndiary.configs;

import com.duongtai.syndiary.entities.Diary;

import java.util.*;

public class SortDiary {
    public static List<Diary> sortByCondition(List<Diary> diaries, String sort){
        List<Diary> diaryList = new ArrayList<>();
        if(Objects.equals(sort, Snippets.HIDDEN)){
            for(Diary diary : diaries){
                if(!diary.isDisplay()){
                    diaryList.add(diary);
                }
            }
            diaryList.sort((o1, o2) -> o1.getLast_edited().compareToIgnoreCase(o2.getLast_edited()));
            Collections.reverse(diaryList);
        }else{
            for(Diary diary : diaries){
                if(diary.isDisplay()){
                    diaryList.add(diary);
                }
            }
            switch (sort) {
                case Snippets.LAST_EDITED:
                    diaryList.sort((o1, o2) -> o1.getLast_edited().compareToIgnoreCase(o2.getLast_edited()));
                    Collections.reverse(diaryList);
                    break;
                case Snippets.CREATED_AT:
                    diaryList.sort((o1, o2) -> o1.getAdded_at().compareToIgnoreCase(o2.getAdded_at()));
                    Collections.reverse(diaryList);
                    break;
                case Snippets.A_Z:
                    diaryList.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
                    break;
                case Snippets.Z_A:
                    diaryList.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
                    Collections.reverse(diaryList);
                    break;
                default:
                    break;
            }
        }

        diaries = diaryList;


        return diaries;
    }
}
