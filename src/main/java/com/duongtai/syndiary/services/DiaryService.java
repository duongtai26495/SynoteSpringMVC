package com.duongtai.syndiary.services;

import com.duongtai.syndiary.entities.Diary;
import com.duongtai.syndiary.entities.User;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public interface DiaryService {

    Diary getById(Long id);

    Diary save_diary(Diary diary);

    Diary update_diary(Diary diary);

    void deleteById(Long id);

    void update_display(Diary diary);

    void update_done(Diary diary);
    
    List<Diary> searchWithKeyword(String keyword);
}
