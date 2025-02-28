package com.unchk.Backend_recruteur.security.services;

import com.unchk.Backend_recruteur.models.Announcement;
import com.unchk.Backend_recruteur.repository.AnnouncementRepository; // Assurez-vous que c'est le bon package
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id).orElse(null);
    }

    public Announcement createAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public Announcement updateAnnouncement(Long id, Announcement announcementDetails) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if (announcement != null) {
            announcement.setTitle(announcementDetails.getTitle());
            announcement.setDescription(announcementDetails.getDescription());
            announcement.setAcademicYear(announcementDetails.getAcademicYear());
            return announcementRepository.save(announcement);
        } else {
            return null;
        }
    }

    public boolean deleteAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if (announcement != null) {
            announcementRepository.delete(announcement);
            return true;
        } else {
            return false;
        }
    }
}