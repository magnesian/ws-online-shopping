package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Customer;
import com.project.ws.domain.Product;
import com.project.ws.domain.Vendor;
import com.project.ws.repository.VendorRepository;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.VendorRepresentation;
import com.project.ws.representation.VendorRequest;

@Component
@Transactional
@Service
public class VendorActivity {
	
	private final VendorRepository vendorRepo;
	
	@Autowired
	Vendor vendor;
	
	@Autowired
	VendorRepresentation vendorRepresentation;
	
	@Autowired
	VendorActivity(VendorRepository vendorRepo) {
		this.vendorRepo = vendorRepo;
	}

	public VendorRepresentation getVendor(Integer vendorId) {
		vendor = vendorRepo.findOne(vendorId);
		return mapRepresentation(vendor);
	}
	
	public VendorRepresentation getVendor(String vendorName) {
		vendor = vendorRepo.findByVendorName(vendorName);
		if(vendor == null) return null;
		vendorRepresentation = mapRepresentation(vendor);
		return vendorRepresentation;
	}
	
	public VendorRepresentation addVendor(VendorRequest vendorRequest) {
		mapRequest(vendorRequest);
		vendorRepo.addVendor(vendor);
		return mapRepresentation(vendor);
	}

	public VendorRepresentation settleAccount(Integer vendorId, Double amount, String type) {
		Double currAmount = 0.00;
		Double newAmount = 0.00;
		currAmount = vendorRepo.findOne(vendorId).getVendorAmount();
		if(type.equals("credit"))
			newAmount = currAmount + amount;
		else if(type.equals("debit"))
			newAmount = currAmount - amount;
		vendorRepo.updatePayment(vendorId, newAmount);
		vendor = vendorRepo.findOne(vendorId);
		return mapRepresentation(vendor);
	}
	
	public VendorRepresentation updateAddress(VendorRequest vendorRequest) {
		vendor = mapRequest(vendorRequest);
		vendorRepo.updateAddress(vendor);
		vendor = vendorRepo.findOne(vendorRequest.getVendorId());
		return mapRepresentation(vendor);
	}

	
	public Boolean validateVendor(Integer vendorId) {
		Vendor v = vendorRepo.findOne(vendorId);
		if(v == null)
			return false;
		else
			return true;
	}
	
	public List<VendorRepresentation> getAllVendors() {
		List<Vendor> vendors = vendorRepo.findAll();
		if(vendors == null) return null;
		List<VendorRepresentation> vendorRepresentations = new ArrayList<VendorRepresentation>();
		for (Vendor vendor : vendors) {
			vendorRepresentations.add(mapRepresentation(vendor));
		}
		return vendorRepresentations;
	}
	
	public VendorRepresentation mapRepresentation(Vendor vendor) {
		vendorRepresentation = new VendorRepresentation();
		vendorRepresentation.setVendorCity(vendor.getVendorCity());
		vendorRepresentation.setVendorCountry(vendor.getVendorCountry());
		vendorRepresentation.setVendorName(vendor.getVendorName());
		vendorRepresentation.setVendorState(vendor.getVendorState());
		vendorRepresentation.setVendorId(vendor.getVendorId());
		return vendorRepresentation;
	}
	
	public Vendor mapRequest(VendorRequest vendorRequest) {
		vendor = new Vendor();
		vendor.setVendorId(vendorRequest.getVendorId());
		vendor.setVendorAddrLine1(vendorRequest.getVendorAddrLine1());
		vendor.setVendorAddrLine2(vendorRequest.getVendorAddrLine2());
		vendor.setVendorCity(vendorRequest.getVendorCity());
		vendor.setVendorCountry(vendorRequest.getVendorCountry());
		vendor.setVendorName(vendorRequest.getVendorName());
		vendor.setVendorState(vendorRequest.getVendorState());
		vendor.setVendorZipCode(vendorRequest.getVendorZipCode());
		return vendor;
	}
}
