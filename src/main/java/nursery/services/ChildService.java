package nursery.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nursery.dao.CheckinRepository;
import nursery.dao.ChildRepository;
import nursery.model.Child;

@Service
@Transactional
public class ChildService {
	private final ChildRepository childRepository;

	@Autowired
	ChildService(CheckinRepository checkinRepository, ChildRepository childRepository) {
		this.childRepository = childRepository;
	}

	public Child findChild(Long id) {
		Child child = childRepository.findOne(id);
		if (child == null) {
			throw new ChildNotFoundException(id);
		}
		return child;
	}

	public Collection<Child> findChildren(Boolean checkedin) {
		if (checkedin == null) {
			return childRepository.findAll();
		}
		return childRepository.findByCheckedin(checkedin);
	}

	public Child saveChild(Child child) {
		return childRepository.save(child);
	}

	public Collection<Child> findAllCheckedin(boolean checkedIn) {
		return childRepository.findByCheckedin(checkedIn);
	}
}
