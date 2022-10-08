package com.duongtai.syndiary.services.impl;

import com.duongtai.syndiary.configs.Snippets;
import com.duongtai.syndiary.entities.Diary;
import com.duongtai.syndiary.entities.User;
import com.duongtai.syndiary.repositories.DiaryRepository;
import com.duongtai.syndiary.services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.duongtai.syndiary.configs.MyUserDetail.getUsernameLogin;

@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public Diary getById(Long id) {
        return diaryRepository.findById(id).get();
    }

    @Override
    public Diary save_diary(Diary diary) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Snippets.TIME_PATTERN);
        diary.setAdded_at(sdf.format(date));
        diary.setLast_edited(sdf.format(date));
        diary.setDone(false);
        diary.setAuthor(userService.findByUsername(getUsernameLogin()));
        return diaryRepository.save(diary);
    }

    @Override
    public Diary update_diary(Diary diary) {
        Diary found_diary = diaryRepository.findById(diary.getId()).get();
        found_diary.setTitle(diary.getTitle());
        found_diary.setContent(diary.getContent());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Snippets.TIME_PATTERN);
        found_diary.setLast_edited(sdf.format(date));
        return diaryRepository.save(found_diary);
    }

    @Override
    public void deleteById(Long id) {
        diaryRepository.deleteById(id);
    }

    @Override
    public void update_display(Diary diary) {
        Diary found_diary = diaryRepository.findById(diary.getId()).get();
        found_diary.setDisplay(diary.isDisplay());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Snippets.TIME_PATTERN);
        found_diary.setLast_edited(sdf.format(date));
        diaryRepository.save(found_diary);
    }

    @Override
    public void update_done(Diary diary) {
        Diary found_diary = diaryRepository.findById(diary.getId()).get();
        found_diary.setDone(diary.isDone());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Snippets.TIME_PATTERN);
        found_diary.setLast_edited(sdf.format(date));
        diaryRepository.save(found_diary);
    }

	@Override
	public List<Diary> searchWithKeyword(String keyword) {
		User user = userService.findByUsername(getUsernameLogin());
		List<Diary> diaries = user.getDiaries();
		List<Diary> found_list = new ArrayList<>();
		
		for(Diary diary : diaries) {
			if(diary.getTitle().toLowerCase().trim().contains(keyword.toLowerCase().trim()) 
					|| diary.getContent().toLowerCase().trim().contains(keyword.toLowerCase().trim())) {
				found_list.add(diary);
			}
		}
		return found_list;
	}

}
