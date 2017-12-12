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
	CheckinService(CheckinRepository checkinRepository, ChildService childRepository) {
		this.checkinRepository = checkinRepository;
		this.childService = childRepository;
	}

	public Collection<Checkin> findByChild(Long childId) {
		Child child = childService.findChild(childId);
		return checkinRepository.findByChild(child);
	}

	public Checkin createCheckin(Long childId) {
		Child child = childService.findChild(childId);
		if (child.getCheckedin()) {
			throw new CheckinException(childId);
		}
		child.setCheckedin(true);
		childService.saveChild(child);

		return checkinRepository.save(new Checkin(child, System.currentTimeMillis()));
	}
}
