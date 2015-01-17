package com.vsp.enterprise.busobj.entity.claim;
import java.util.*;

import com.vsp.arch.data.types.TriStateBoolean;
import com.vsp.enterprise.busobj.entity.benefit.Benefit;
import com.vsp.enterprise.code.ClaimTypeCode;
import com.vsp.enterprise.code.HoldReasonCode;
import com.vsp.enterprise.entity.BusinessEntity;


public class Claim {
	private String HCFABox19;
	private ClaimTypeCode type;
	private String docReview = "";
	private boolean denialPending = false;
	private boolean droolsHistoryFired = false;
	private PhysicianInvoice physicianInvoice;
	private List<Message> messages = new ArrayList<Message>();
	private BusinessEntity businessEntity = new BusinessEntity();
	private OtherInsured otherInsured = new OtherInsured(TriStateBoolean.FALSE);
	private Benefit benefit;
	
	private com.vsp.enterprise.busobj.entity.benefit.Benefit Benefit;
	private com.vsp.enterprise.code.ClaimStatusCode AllStatus;
	private List<AttachmentType> attachmentTypes;
	private com.vsp.enterprise.busobj.entity.claim.AttachmentTypeList AttachmentTypeList;
	private String BenefitRequest;
	private String BenefitRequestNumber;
	private ClaimMember ClaimMember;
	private java.lang.String ClaimNumber;
	private String COBNumber;
	private com.vsp.enterprise.code.ContactLensReasonCode ContactLensTypeCode;
	private com.vsp.arch.data.types.VSPDate CurrentIllnessOnsetDate;
	private com.vsp.enterprise.code.DocumentReviewCode DocReview;
	private com.vsp.arch.data.types.VSPDate DoctorSignatureDateSigned;
	private java.lang.Boolean DoctorSignatureIndicator;
	private java.lang.Boolean ClaimManualDenyIndicator;
	private java.lang.Boolean ExamServiceIndicator;
	private java.lang.String ExtensionNumber;
	private java.lang.Boolean FrameServiceIndicator;
	private com.vsp.enterprise.code.FrameSuppliedByCode FrameSuppliedBy;
	private com.vsp.arch.data.types.Currency FSAAmount;
	private GroupInvoice GroupInvoice;
	private HoldReasonCode Hold;
	private com.vsp.arch.data.types.VSPDate HospitalizationEndDate;
	private com.vsp.arch.data.types.VSPDate HospitalizationStartDate;
	private com.vsp.enterprise.code.InsuranceTypeCode InsuranceType;
	private com.vsp.enterprise.code.OONPayeeCode OONPayee;
	private com.vsp.arch.data.types.VSPDate JobDisabledEndDate;
	private com.vsp.arch.data.types.VSPDate JobDisabledStartDate;
	private com.vsp.enterprise.busobj.entity.claim.LabInvoice LabInvoice;
	private java.lang.Boolean LensServiceIndicator;
	private com.vsp.arch.data.types.VSPDate MemberSignatureDateSigned;
	private java.lang.Boolean MemberSignatureIndicator;
	private boolean NewLineAdded;
	private java.lang.Boolean ModifiableIndicator;
	private java.lang.String OriginalReferenceClaimNumber;
	private com.vsp.enterprise.busobj.entity.claim.OtherInsured OtherInsured;
	private com.vsp.arch.data.types.Currency OutsideLabCharges;
	private java.lang.Boolean OutsideLabIndicator;
	private com.vsp.enterprise.busobj.patient.Patient Patient;
	private com.vsp.arch.data.types.VSPDate PatientSignatureDateSigned;
	private java.lang.Boolean PatientSignatureIndicator;
	private com.vsp.enterprise.busobj.entity.claim.PhysicianInvoice PhysicianInvoice;
	private com.vsp.enterprise.busobj.entity.claim.PlaceOfService PlaceOfService;
	private com.vsp.enterprise.busobj.entity.claim.PricingServiceLocation PricingServiceLocaton;
	private com.vsp.arch.data.types.VSPDate PremiumEndDate;
	private com.vsp.arch.data.types.VSPDate PremiumStartDate;
	private java.lang.String PriorAuthorizationNumber;
	private com.vsp.arch.data.types.VSPDate PriorIllnessOnsetDate;
	private com.vsp.enterprise.busobj.patient.Patient Program;
	private com.vsp.enterprise.busobj.entity.claim.Referral Referral;
	private com.vsp.enterprise.busobj.entity.claim.ReferringProvider ReferringProvider;
	private com.vsp.enterprise.busobj.entity.claim.RenderingProvider RenderingProvider;
	private java.lang.Boolean RepairReplaceIndicator;
	private com.vsp.enterprise.code.ShareOfCostIndicatorCode ShareOfCost;
	private java.lang.String SpecialInstructions;
	private com.vsp.enterprise.code.ClaimStatusCode Status;
	private java.lang.String DupOrigBRNumber;
	private com.vsp.enterprise.code.ClaimTypeCode Type;
	private com.vsp.arch.data.types.Currency ClaimWriteOffAmount;
	private String PrevUpdateId;
	private boolean BRStatusChangedIndicator;
	private Benefit OONBenefit;
	private boolean CreateClaimFunctionalityIndicator;
	private String Original837BRNumber;
	private String OriginalZOrderNumber;
	private String CheckFormCode;
	private String WorkAroundNumber;
	private String FrameBrandName;
	private String IncomingFrameBrandName;
	private String FrameUPC;
	
	public Claim() {
	}

	public Claim(BusinessEntity businessEntity) {
		this.businessEntity = businessEntity;
	}

	public String getHCFABox19() {
		return HCFABox19;
	}

	public void setHCFABox19(String hCFABox19) {
		HCFABox19 = hCFABox19;
	}

	public ClaimTypeCode getType() {
		return type;
	}

	public void setType(ClaimTypeCode type) {
		this.type = type;
	}

	public String getDocReview() {
		return docReview;
	}

	public void setDocReview(String docReview) {
		this.docReview = docReview;
	}

	public boolean isDenialPending() {
		return denialPending;
	}

	public void setDenialPending(boolean denialPending) {
		this.denialPending = denialPending;
	}
	
	public PhysicianInvoice getPhysicianInvoice() {
		return physicianInvoice;
	}

	public void setPhysicianInvoice(PhysicianInvoice physicianInvoice) {
		this.physicianInvoice = physicianInvoice;
	}

	public boolean hasDroolsHistoryFired(String droolName) {
		return droolsHistoryFired;
	}

	public void setDroolsHistoryFired(boolean droolsHistoryFired) {
		this.droolsHistoryFired = droolsHistoryFired;
	}

	public void addDroolsHistory(String ruleName) {
		
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	public Message[] getMessage() {
		return messages.toArray(new Message[0]);
	}

	public BusinessEntity getBusinessEntity() {
		return businessEntity;
	}

	public void setBusinessEntity(BusinessEntity businessEntity) {
		this.businessEntity = businessEntity;
	}

	public OtherInsured getOtherInsured() {
		return otherInsured;
	}

	public void setOtherInsured(OtherInsured otherInsured) {
		this.otherInsured = otherInsured;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Benefit getBenefit() {
		return benefit;
	}

	public void setBenefit(Benefit benefit) {
		this.benefit = benefit;
	}

	public com.vsp.enterprise.code.ClaimStatusCode getAllStatus() {
		return AllStatus;
	}

	public void setAllStatus(com.vsp.enterprise.code.ClaimStatusCode allStatus) {
		AllStatus = allStatus;
	}

	public List<AttachmentType> getAttachmentTypes() {
		return attachmentTypes;
	}

	public void setAttachmentTypes(List<AttachmentType> attachmentTypes) {
		this.attachmentTypes = attachmentTypes;
	}

	public com.vsp.enterprise.busobj.entity.claim.AttachmentTypeList getAttachmentTypeList() {
		return AttachmentTypeList;
	}

	public void setAttachmentTypeList(
			com.vsp.enterprise.busobj.entity.claim.AttachmentTypeList attachmentTypeList) {
		AttachmentTypeList = attachmentTypeList;
	}

	public String getBenefitRequest() {
		return BenefitRequest;
	}

	public void setBenefitRequest(String benefitRequest) {
		BenefitRequest = benefitRequest;
	}

	public String getBenefitRequestNumber() {
		return BenefitRequestNumber;
	}

	public void setBenefitRequestNumber(String benefitRequestNumber) {
		BenefitRequestNumber = benefitRequestNumber;
	}

	public ClaimMember getClaimMember() {
		return ClaimMember;
	}

	public void setClaimMember(ClaimMember claimMember) {
		ClaimMember = claimMember;
	}

	public java.lang.String getClaimNumber() {
		return ClaimNumber;
	}

	public void setClaimNumber(java.lang.String claimNumber) {
		ClaimNumber = claimNumber;
	}

	public String getCOBNumber() {
		return COBNumber;
	}

	public void setCOBNumber(String cOBNumber) {
		COBNumber = cOBNumber;
	}

	public com.vsp.enterprise.code.ContactLensReasonCode getContactLensTypeCode() {
		return ContactLensTypeCode;
	}

	public void setContactLensTypeCode(
			com.vsp.enterprise.code.ContactLensReasonCode contactLensTypeCode) {
		ContactLensTypeCode = contactLensTypeCode;
	}

	public com.vsp.arch.data.types.VSPDate getCurrentIllnessOnsetDate() {
		return CurrentIllnessOnsetDate;
	}

	public void setCurrentIllnessOnsetDate(
			com.vsp.arch.data.types.VSPDate currentIllnessOnsetDate) {
		CurrentIllnessOnsetDate = currentIllnessOnsetDate;
	}

	public com.vsp.arch.data.types.VSPDate getDoctorSignatureDateSigned() {
		return DoctorSignatureDateSigned;
	}

	public void setDoctorSignatureDateSigned(
			com.vsp.arch.data.types.VSPDate doctorSignatureDateSigned) {
		DoctorSignatureDateSigned = doctorSignatureDateSigned;
	}

	public java.lang.Boolean getDoctorSignatureIndicator() {
		return DoctorSignatureIndicator;
	}

	public void setDoctorSignatureIndicator(
			java.lang.Boolean doctorSignatureIndicator) {
		DoctorSignatureIndicator = doctorSignatureIndicator;
	}

	public java.lang.Boolean getClaimManualDenyIndicator() {
		return ClaimManualDenyIndicator;
	}

	public void setClaimManualDenyIndicator(
			java.lang.Boolean claimManualDenyIndicator) {
		ClaimManualDenyIndicator = claimManualDenyIndicator;
	}

	public java.lang.Boolean getExamServiceIndicator() {
		return ExamServiceIndicator;
	}

	public void setExamServiceIndicator(java.lang.Boolean examServiceIndicator) {
		ExamServiceIndicator = examServiceIndicator;
	}

	public java.lang.String getExtensionNumber() {
		return ExtensionNumber;
	}

	public void setExtensionNumber(java.lang.String extensionNumber) {
		ExtensionNumber = extensionNumber;
	}

	public java.lang.Boolean getFrameServiceIndicator() {
		return FrameServiceIndicator;
	}

	public void setFrameServiceIndicator(java.lang.Boolean frameServiceIndicator) {
		FrameServiceIndicator = frameServiceIndicator;
	}

	public com.vsp.enterprise.code.FrameSuppliedByCode getFrameSuppliedBy() {
		return FrameSuppliedBy;
	}

	public void setFrameSuppliedBy(
			com.vsp.enterprise.code.FrameSuppliedByCode frameSuppliedBy) {
		FrameSuppliedBy = frameSuppliedBy;
	}

	public com.vsp.arch.data.types.Currency getFSAAmount() {
		return FSAAmount;
	}

	public void setFSAAmount(com.vsp.arch.data.types.Currency fSAAmount) {
		FSAAmount = fSAAmount;
	}

	public GroupInvoice getGroupInvoice() {
		return GroupInvoice;
	}

	public void setGroupInvoice(GroupInvoice groupInvoice) {
		GroupInvoice = groupInvoice;
	}

	public HoldReasonCode getHold() {
		return Hold;
	}

	public void setHold(HoldReasonCode hold) {
		Hold = hold;
	}

	public com.vsp.arch.data.types.VSPDate getHospitalizationEndDate() {
		return HospitalizationEndDate;
	}

	public void setHospitalizationEndDate(
			com.vsp.arch.data.types.VSPDate hospitalizationEndDate) {
		HospitalizationEndDate = hospitalizationEndDate;
	}

	public com.vsp.arch.data.types.VSPDate getHospitalizationStartDate() {
		return HospitalizationStartDate;
	}

	public void setHospitalizationStartDate(
			com.vsp.arch.data.types.VSPDate hospitalizationStartDate) {
		HospitalizationStartDate = hospitalizationStartDate;
	}

	public com.vsp.enterprise.code.InsuranceTypeCode getInsuranceType() {
		return InsuranceType;
	}

	public void setInsuranceType(
			com.vsp.enterprise.code.InsuranceTypeCode insuranceType) {
		InsuranceType = insuranceType;
	}

	public com.vsp.enterprise.code.OONPayeeCode getOONPayee() {
		return OONPayee;
	}

	public void setOONPayee(com.vsp.enterprise.code.OONPayeeCode oONPayee) {
		OONPayee = oONPayee;
	}

	public com.vsp.arch.data.types.VSPDate getJobDisabledEndDate() {
		return JobDisabledEndDate;
	}

	public void setJobDisabledEndDate(
			com.vsp.arch.data.types.VSPDate jobDisabledEndDate) {
		JobDisabledEndDate = jobDisabledEndDate;
	}

	public com.vsp.arch.data.types.VSPDate getJobDisabledStartDate() {
		return JobDisabledStartDate;
	}

	public void setJobDisabledStartDate(
			com.vsp.arch.data.types.VSPDate jobDisabledStartDate) {
		JobDisabledStartDate = jobDisabledStartDate;
	}

	public com.vsp.enterprise.busobj.entity.claim.LabInvoice getLabInvoice() {
		return LabInvoice;
	}

	public void setLabInvoice(
			com.vsp.enterprise.busobj.entity.claim.LabInvoice labInvoice) {
		LabInvoice = labInvoice;
	}

	public java.lang.Boolean getLensServiceIndicator() {
		return LensServiceIndicator;
	}

	public void setLensServiceIndicator(java.lang.Boolean lensServiceIndicator) {
		LensServiceIndicator = lensServiceIndicator;
	}

	public com.vsp.arch.data.types.VSPDate getMemberSignatureDateSigned() {
		return MemberSignatureDateSigned;
	}

	public void setMemberSignatureDateSigned(
			com.vsp.arch.data.types.VSPDate memberSignatureDateSigned) {
		MemberSignatureDateSigned = memberSignatureDateSigned;
	}

	public java.lang.Boolean getMemberSignatureIndicator() {
		return MemberSignatureIndicator;
	}

	public void setMemberSignatureIndicator(
			java.lang.Boolean memberSignatureIndicator) {
		MemberSignatureIndicator = memberSignatureIndicator;
	}

	public boolean isNewLineAdded() {
		return NewLineAdded;
	}

	public void setNewLineAdded(boolean newLineAdded) {
		NewLineAdded = newLineAdded;
	}

	public java.lang.Boolean getModifiableIndicator() {
		return ModifiableIndicator;
	}

	public void setModifiableIndicator(java.lang.Boolean modifiableIndicator) {
		ModifiableIndicator = modifiableIndicator;
	}

	public java.lang.String getOriginalReferenceClaimNumber() {
		return OriginalReferenceClaimNumber;
	}

	public void setOriginalReferenceClaimNumber(
			java.lang.String originalReferenceClaimNumber) {
		OriginalReferenceClaimNumber = originalReferenceClaimNumber;
	}

	public com.vsp.arch.data.types.Currency getOutsideLabCharges() {
		return OutsideLabCharges;
	}

	public void setOutsideLabCharges(
			com.vsp.arch.data.types.Currency outsideLabCharges) {
		OutsideLabCharges = outsideLabCharges;
	}

	public java.lang.Boolean getOutsideLabIndicator() {
		return OutsideLabIndicator;
	}

	public void setOutsideLabIndicator(java.lang.Boolean outsideLabIndicator) {
		OutsideLabIndicator = outsideLabIndicator;
	}

	public com.vsp.enterprise.busobj.patient.Patient getPatient() {
		return Patient;
	}

	public void setPatient(com.vsp.enterprise.busobj.patient.Patient patient) {
		Patient = patient;
	}

	public com.vsp.arch.data.types.VSPDate getPatientSignatureDateSigned() {
		return PatientSignatureDateSigned;
	}

	public void setPatientSignatureDateSigned(
			com.vsp.arch.data.types.VSPDate patientSignatureDateSigned) {
		PatientSignatureDateSigned = patientSignatureDateSigned;
	}

	public java.lang.Boolean getPatientSignatureIndicator() {
		return PatientSignatureIndicator;
	}

	public void setPatientSignatureIndicator(
			java.lang.Boolean patientSignatureIndicator) {
		PatientSignatureIndicator = patientSignatureIndicator;
	}

	public com.vsp.enterprise.busobj.entity.claim.PlaceOfService getPlaceOfService() {
		return PlaceOfService;
	}

	public void setPlaceOfService(
			com.vsp.enterprise.busobj.entity.claim.PlaceOfService placeOfService) {
		PlaceOfService = placeOfService;
	}

	public com.vsp.enterprise.busobj.entity.claim.PricingServiceLocation getPricingServiceLocaton() {
		return PricingServiceLocaton;
	}

	public void setPricingServiceLocaton(
			com.vsp.enterprise.busobj.entity.claim.PricingServiceLocation pricingServiceLocaton) {
		PricingServiceLocaton = pricingServiceLocaton;
	}

	public com.vsp.arch.data.types.VSPDate getPremiumEndDate() {
		return PremiumEndDate;
	}

	public void setPremiumEndDate(com.vsp.arch.data.types.VSPDate premiumEndDate) {
		PremiumEndDate = premiumEndDate;
	}

	public com.vsp.arch.data.types.VSPDate getPremiumStartDate() {
		return PremiumStartDate;
	}

	public void setPremiumStartDate(com.vsp.arch.data.types.VSPDate premiumStartDate) {
		PremiumStartDate = premiumStartDate;
	}

	public java.lang.String getPriorAuthorizationNumber() {
		return PriorAuthorizationNumber;
	}

	public void setPriorAuthorizationNumber(
			java.lang.String priorAuthorizationNumber) {
		PriorAuthorizationNumber = priorAuthorizationNumber;
	}

	public com.vsp.arch.data.types.VSPDate getPriorIllnessOnsetDate() {
		return PriorIllnessOnsetDate;
	}

	public void setPriorIllnessOnsetDate(
			com.vsp.arch.data.types.VSPDate priorIllnessOnsetDate) {
		PriorIllnessOnsetDate = priorIllnessOnsetDate;
	}

	public com.vsp.enterprise.busobj.patient.Patient getProgram() {
		return Program;
	}

	public void setProgram(com.vsp.enterprise.busobj.patient.Patient program) {
		Program = program;
	}

	public com.vsp.enterprise.busobj.entity.claim.Referral getReferral() {
		return Referral;
	}

	public void setReferral(com.vsp.enterprise.busobj.entity.claim.Referral referral) {
		Referral = referral;
	}

	public com.vsp.enterprise.busobj.entity.claim.ReferringProvider getReferringProvider() {
		return ReferringProvider;
	}

	public void setReferringProvider(
			com.vsp.enterprise.busobj.entity.claim.ReferringProvider referringProvider) {
		ReferringProvider = referringProvider;
	}

	public com.vsp.enterprise.busobj.entity.claim.RenderingProvider getRenderingProvider() {
		return RenderingProvider;
	}

	public void setRenderingProvider(
			com.vsp.enterprise.busobj.entity.claim.RenderingProvider renderingProvider) {
		RenderingProvider = renderingProvider;
	}

	public java.lang.Boolean getRepairReplaceIndicator() {
		return RepairReplaceIndicator;
	}

	public void setRepairReplaceIndicator(java.lang.Boolean repairReplaceIndicator) {
		RepairReplaceIndicator = repairReplaceIndicator;
	}

	public com.vsp.enterprise.code.ShareOfCostIndicatorCode getShareOfCost() {
		return ShareOfCost;
	}

	public void setShareOfCost(
			com.vsp.enterprise.code.ShareOfCostIndicatorCode shareOfCost) {
		ShareOfCost = shareOfCost;
	}

	public java.lang.String getSpecialInstructions() {
		return SpecialInstructions;
	}

	public void setSpecialInstructions(java.lang.String specialInstructions) {
		SpecialInstructions = specialInstructions;
	}

	public com.vsp.enterprise.code.ClaimStatusCode getStatus() {
		return Status;
	}

	public void setStatus(com.vsp.enterprise.code.ClaimStatusCode status) {
		Status = status;
	}

	public java.lang.String getDupOrigBRNumber() {
		return DupOrigBRNumber;
	}

	public void setDupOrigBRNumber(java.lang.String dupOrigBRNumber) {
		DupOrigBRNumber = dupOrigBRNumber;
	}

	public com.vsp.arch.data.types.Currency getClaimWriteOffAmount() {
		return ClaimWriteOffAmount;
	}

	public void setClaimWriteOffAmount(
			com.vsp.arch.data.types.Currency claimWriteOffAmount) {
		ClaimWriteOffAmount = claimWriteOffAmount;
	}

	public String getPrevUpdateId() {
		return PrevUpdateId;
	}

	public void setPrevUpdateId(String prevUpdateId) {
		PrevUpdateId = prevUpdateId;
	}

	public boolean isBRStatusChangedIndicator() {
		return BRStatusChangedIndicator;
	}

	public void setBRStatusChangedIndicator(boolean bRStatusChangedIndicator) {
		BRStatusChangedIndicator = bRStatusChangedIndicator;
	}

	public Benefit getOONBenefit() {
		return OONBenefit;
	}

	public void setOONBenefit(Benefit oONBenefit) {
		OONBenefit = oONBenefit;
	}

	public boolean isCreateClaimFunctionalityIndicator() {
		return CreateClaimFunctionalityIndicator;
	}

	public void setCreateClaimFunctionalityIndicator(
			boolean createClaimFunctionalityIndicator) {
		CreateClaimFunctionalityIndicator = createClaimFunctionalityIndicator;
	}

	public String getOriginal837BRNumber() {
		return Original837BRNumber;
	}

	public void setOriginal837BRNumber(String original837brNumber) {
		Original837BRNumber = original837brNumber;
	}

	public String getOriginalZOrderNumber() {
		return OriginalZOrderNumber;
	}

	public void setOriginalZOrderNumber(String originalZOrderNumber) {
		OriginalZOrderNumber = originalZOrderNumber;
	}

	public String getCheckFormCode() {
		return CheckFormCode;
	}

	public void setCheckFormCode(String checkFormCode) {
		CheckFormCode = checkFormCode;
	}

	public String getWorkAroundNumber() {
		return WorkAroundNumber;
	}

	public void setWorkAroundNumber(String workAroundNumber) {
		WorkAroundNumber = workAroundNumber;
	}

	public String getFrameBrandName() {
		return FrameBrandName;
	}

	public void setFrameBrandName(String frameBrandName) {
		FrameBrandName = frameBrandName;
	}

	public String getIncomingFrameBrandName() {
		return IncomingFrameBrandName;
	}

	public void setIncomingFrameBrandName(String incomingFrameBrandName) {
		IncomingFrameBrandName = incomingFrameBrandName;
	}

	public String getFrameUPC() {
		return FrameUPC;
	}

	public void setFrameUPC(String frameUPC) {
		FrameUPC = frameUPC;
	}

	public boolean isDroolsHistoryFired() {
		return droolsHistoryFired;
	}

	public void setDocReview(com.vsp.enterprise.code.DocumentReviewCode docReview) {
		DocReview = docReview;
	}
	
	
}
