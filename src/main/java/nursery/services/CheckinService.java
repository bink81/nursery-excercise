package nursery.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nursery.dao.CheckinRepository;
import nursery.model.Checkin;
import nursery.model.Child;

@Service
@Transactional
public class CheckinService {
    private final CheckinRepository checkinRepository;

    private final ChildService childService;

    @Autowired
    CheckinService(final CheckinRepository checkinRepository,
            final ChildService childRepository) {
        this.checkinRepository = checkinRepository;
        this.childService = childRepository;
    }

    public Collection<Checkin> findByChild(final Long childId) {
        final Child child = this.childService.findChild(childId);
        return this.checkinRepository.findByChild(child);
    }

    public Checkin createCheckin(final Long childId) {
        final Child child = this.childService.findChild(childId);
        if (child.getCheckedin()) {
            throw new CheckinException(childId);
        }
        child.setCheckedin(true);
        this.childService.saveChild(child);

        return this.checkinRepository
                .save(new Checkin(child, System.currentTimeMillis()));
    }
}
