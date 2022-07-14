package com.abc.CabBookingApplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.abc.CabBookingApplication.entity.Cab;
import com.abc.CabBookingApplication.exception.ResourceNotFoundException;
import com.abc.CabBookingApplication.repository.CabRepository;

@SpringBootTest
public class CabServiceTest {
	@Mock
	private CabRepository cabRepository;
	@InjectMocks
	private CabServiceImpl cabServiceImpl;

	@Test
	public void testInsertCab() {
		Cab cab = new Cab();

		cab.setCabId(1);
		cab.setCarType("SUV");
		cab.setPerKmRate(20);

		when(cabRepository.save(cab)).thenReturn(cab);

		Cab newCab = cabServiceImpl.insertCab(cab);

		assertEquals(cab.getCabId(), newCab.getCabId());

		assertEquals(cab.getCarType(), newCab.getCarType());

		assertEquals(cab.getPerKmRate(), newCab.getPerKmRate());
	}

	@Test
	public void testDeleteCab() {
		Cab cab = new Cab();

		cab.setCabId(1);
		cab.setCarType("SUV");
		cab.setPerKmRate(20);

		Optional<Cab> optionalCab = Optional.of(cab);
		when(cabRepository.findById(1)).thenReturn(optionalCab);
		doNothing().when(cabRepository).delete(optionalCab.get());// void methods

		cabServiceImpl.deleteCab(1);

		verify(cabRepository, times(1)).delete(optionalCab.get());

	}

	@Test
	public void testGetCabById() {
		Cab cab = new Cab();

		cab.setCabId(1);
		cab.setCarType("SUV");
		cab.setPerKmRate(20);

		Optional<Cab> optionalCab = Optional.of(cab);
		when(cabRepository.findById(1)).thenReturn(optionalCab);

		Cab actualCab = cabServiceImpl.getCabById(1);

		Cab expectedCab = optionalCab.get();

		assertEquals(expectedCab.getCabId(), actualCab.getCabId());
		assertEquals(expectedCab.getCarType(), actualCab.getCarType());
	}

	@Test
	public void testGetCabtByIdThrowsException() {

		when(cabRepository.findById(1)).thenThrow(ResourceNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> cabServiceImpl.getCabById(1));

	}

	@Test
	public void testGetCabsByType() {
		List<Cab> cabs = new ArrayList<>();
		Cab cab1 = new Cab();

		cab1.setCabId(1);
		cab1.setCarType("SUV");
		cab1.setPerKmRate(20);
		cabs.add(cab1);

		Cab cab2 = new Cab();

		cab2.setCabId(2);
		cab2.setCarType("SUV");
		cab2.setPerKmRate(20);
		cabs.add(cab2);

		Optional<List<Cab>> optionalCabs = Optional.of(cabs);

		when(cabRepository.findByType("SUV")).thenReturn(optionalCabs.get());

		List<Cab> actual = cabServiceImpl.viewCabsOfType("SUV");

		assertEquals(optionalCabs.get(),actual);
		// assertEquals(cabs.contains(cabs),optionalCabs.containsAll(cabs));

	}

	@Test
	public void testGetCabTypeCount() {
		List<Cab> cabs = new ArrayList<>();
		Cab cab1 = new Cab();

		cab1.setCabId(1);
		cab1.setCarType("SUV");
		cab1.setPerKmRate(20);
		cabs.add(cab1);

		Cab cab2 = new Cab();

		cab2.setCabId(2);
		cab2.setCarType("SUV");
		cab2.setPerKmRate(20);
		cabs.add(cab2);
		Optional<List<Cab>> optionalCabs = Optional.of(cabs);

		when(cabRepository.findByType("SUV")).thenReturn(optionalCabs.get());
		int count = optionalCabs.get().size();
		int actual = cabServiceImpl.countCabsOfType("SUV");
		assertEquals(count,actual);
	}

}
