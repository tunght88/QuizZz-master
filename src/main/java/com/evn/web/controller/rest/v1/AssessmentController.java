package com.evn.web.controller.rest.v1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STOnOff1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evn.web.controller.utils.SendEmailSSL;
import com.evn.web.controller.utils.Utils;
import com.evn.web.model.Assessment;
import com.evn.web.model.AssessmentResult;
import com.evn.web.model.AuthenticatedUser;
import com.evn.web.model.Member;
import com.evn.web.model.support.AssessmentView;
import com.evn.web.model.support.Response;
import com.evn.web.service.AssessmentService;

@RestController
@RequestMapping(AssessmentController.ROOT_MAPPING)
public class AssessmentController {

	public static final String ROOT_MAPPING = "/api/assessments";
	
	private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

	@Autowired
	private AssessmentService assessmentService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public Page<AssessmentView> findAll(Pageable pageable
			,@AuthenticationPrincipal AuthenticatedUser user) {
		if(user == null)
			return null;
		return assessmentService.getAssessmentsByUser(user.getUser(), pageable);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public Page<AssessmentView> findAll2(Pageable pageable
			,@AuthenticationPrincipal AuthenticatedUser user) {
		if(user == null)
			return null;
		return assessmentService.getAssessmentsCreateByUser(user.getUser(), pageable);
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Page<Assessment> searchAll(Pageable pageable, @RequestParam(required = true) String filter,
			@RequestParam(required = false, defaultValue = "false") Boolean onlyValid) {

		return assessmentService.search(filter, pageable);
	}


	@RequestMapping(value = "/{assessment_id}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Assessment find(@PathVariable Long assessment_id) {

		return assessmentService.find(assessment_id);
	}

	@RequestMapping(value = "/{assessment_id}/last-result", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public AssessmentResult findAssessmentResult(@PathVariable Long assessment_id,@AuthenticationPrincipal AuthenticatedUser user) {

		return assessmentService.findActiveResult(assessment_id, user.getId());
	}

	@RequestMapping(value = "/{assessment_id}/submit", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity  submitResult(@PathVariable long assessment_id, @RequestBody Response resp,@AuthenticationPrincipal AuthenticatedUser user) {
		Assessment assessment = assessmentService.find(assessment_id);
		if(resp.getId() != null) {
			AssessmentResult old = assessmentService.findAssessmentResultById(Long.valueOf(resp.getId()));
			old.setActive(false);
			assessmentService.save(old);
		}
			
		AssessmentResult result = new AssessmentResult(resp);
		result.setCreatedDate(Calendar.getInstance());
		result.setAssessment(assessment);
		if(user != null)
			result = assessmentService.save(result,user.getUser());
		else
			result = assessmentService.save(result);
		File file = generate02(assessment, result);
		String toEmail = "robintungyb@gmail.com, to_username_b@yahoo.com";
		String subject = "Testing Gmail SSL";
		String body = "Dear Mail Crawler,\" + \"\\n\\n Please do not spam my email!";
		SendEmailSSL.send(toEmail, subject, body, file);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/{assessment_id}/export03", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resource>  export03(@PathVariable long assessment_id,@AuthenticationPrincipal AuthenticatedUser user) {
		Assessment assessment = assessmentService.find(assessment_id);
		List<AssessmentResult> results  = assessmentService.findAllByAssessmentId(assessment_id);
		XWPFDocument document = null;
		File file = null;
		OutputStream os = null;
		Resource resource = null;
		Map<String,Object> map = new HashMap<>();
		Integer total = results.size();
		Integer countFail= 0;
		Integer countSuccess = 0;
		Float s_4_5_1 =0f;
		Integer c_4_5_1= 0;
		Float s_4_5_2 =0f;
		Integer c_4_5_2= 0;
		Float s_4_6 =0f;
		Integer c_4_6 =0;
		Integer countUpper = 0;
		final DecimalFormat df = new DecimalFormat("0.##");
		for (AssessmentResult result : results) {
			if(result.getV_4() == 0)
				countFail ++;
			else 
				countSuccess++;
			if(result.getV_4_7() != null && result.getV_4_7() == 1)
				countUpper ++;
			if(result.getV_4_5_1() != null) {
				c_4_5_1 ++;
				s_4_5_1 +=result.getV_4_5_1();
			}
			if(result.getV_4_5_2() != null) {
				c_4_5_2 ++;
				s_4_5_2 +=result.getV_4_5_2();
			}
			if(result.getV_4_6() != null) {
				c_4_6 ++;
				s_4_6 +=result.getV_4_6();
			}
		}
		float failRate = countFail*100/total;
		float upperRate = countUpper*100/total;
		String result = failRate <= 33 ? "Công nhận" : "Không công nhận";
		String upperResult = upperRate >= 75 ? "e) Đề xuất đăng ký công nhận cấp cao hơn" : "";
		map.put("{result}", result);
		map.put("{upperResult}", upperResult);
		map.put("{org1}", assessment.getOrg1());
		map.put("{org2}", assessment.getOrg2());
		map.put("{total}", total);
		map.put("{fail}", countFail);
		map.put("{success}", countSuccess);
		map.put("{upper}", countUpper);
		map.put("{v_4_5_1}", c_4_5_1 > 0 ? df.format(s_4_5_1/c_4_5_1) : "");
		map.put("{v_4_5_2}", c_4_5_2 > 0 ? df.format(s_4_5_2/c_4_5_2) : "");
		map.put("{v_4_6}", c_4_6 > 0 ? df.format(s_4_6/c_4_6) : "");
		map.put("{ideaName}", assessment.getIdea().getText());
		map.put("{level}", assessment.getLevel().getText());
		map.put("{member}", assessment.getIdea().getMembers());
		map.put("{president}", assessment.getCouncil().getPresident());
		map.put("{secretary}", assessment.getCouncil().getSecretary());
		map.put("{councilUnit}", assessment.getCouncil().getUnit());
		map.put("{councilName}", assessment.getCouncil().getText());
		map.put("{decisionNumber}", assessment.getCouncil().getDecisionNumber());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		map.put("{meetingStart}", sdf.format(assessment.getCouncil().getStartDate().getTime()));
		map.put("{meetingEnd}", sdf.format(assessment.getCouncil().getEndDate().getTime()));
		map.put("{meetingLoc}", assessment.getCouncil().getMeetingLoc());
		map.put("{meetingType}", assessment.getCouncil().getMeetingType());
		map.put("{day}", assessment.getCouncil().getFoundingDate().get(Calendar.DAY_OF_MONTH) +"");
		map.put("{month}", assessment.getCouncil().getFoundingDate().get(Calendar.MONTH) +"");
		map.put("{year}", assessment.getCouncil().getFoundingDate().get(Calendar.YEAR) +"");
		map.put("{day1}", assessment.getCouncil().getStartDate().get(Calendar.DAY_OF_MONTH) +"");
		map.put("{month1}", assessment.getCouncil().getStartDate().get(Calendar.MONTH) +"");
		map.put("{year1}", assessment.getCouncil().getStartDate().get(Calendar.YEAR) +"");
		try {
			file = ResourceUtils.getFile("classpath:static/word/BM03.docx");
			document = new XWPFDocument(new FileInputStream(file));
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            List<XWPFTable> tables = document.getTables();
            List<XWPFParagraph> all = new ArrayList<>();
            for (int i = 0; i < tables.size(); i++) {

            	XWPFTable table = tables.get(i);
                List<XWPFTableRow> rows = table.getRows();
                for (int j = 0; j < rows.size(); j++) {
                	XWPFTableRow row = rows.get(j);
                    List<XWPFTableCell> cells = row.getTableCells();

                    for (int k = 0; k < cells.size(); k++) {
                    	XWPFTableCell cell = cells.get(k);
                    	all.addAll(cell.getParagraphs());
                    }
                	
                }
            }
            all.addAll(paragraphs);
            for (int i = 0; i < all.size(); i++) {
            	XWPFParagraph xwpfParagraph = all.get(i);
	            	List<XWPFRun> runs = xwpfParagraph.getRuns();
	            	
	            	for (int j = 0; j < runs.size(); j++) {
	            		XWPFRun xwpfRun = runs.get(j);
	            		//text
	            		String text= xwpfRun.getText(0) == null ? "" : xwpfRun.getText(0).trim();
	            		if(map.containsKey(text)) {
	            			if(map.get(text) == null || map.get(text).equals("")) {
	            				xwpfRun.setText("",0);
	            			}
	            			if(map.get(text) instanceof  String){
	            				xwpfRun.setText(String.valueOf(map.get(text)),0);
	            			}
	            			if(map.get(text) instanceof  Integer){
	            				xwpfRun.setText(String.valueOf(map.get(text)),0);
	            			}
	            			if(map.get(xwpfRun.getText(0)) instanceof  List) {
	            				boolean first = true;
	            				for (Member member : (List<Member>)map.get(xwpfRun.getText(0))) {
	            					if(first) {
	            						xwpfRun.setText(member.getText(),0);
	            						first = false;
	            					}else {
		    	            			XmlCursor cursor = xwpfParagraph.getCTP().newCursor();
		    	            			cursor.toNextSibling();
		    	            			XWPFParagraph paragraph =  document.insertNewParagraph(cursor);
		            					XWPFRun run = paragraph.createRun();
		            					CTR ctr = (CTR)xwpfRun.getCTR().copy();
		            					run.getCTR().set(ctr);
	            						run.setText(member.getText(),0);
	            						paragraph.addRun(run);
	            						paragraph.setNumID(xwpfParagraph.getNumID());
	            						paragraph.setSpacingAfter(xwpfParagraph.getSpacingAfter());
	            						paragraph.setSpacingBefore(xwpfParagraph.getSpacingBefore());
	            						paragraph.setSpacingLineRule(xwpfParagraph.getSpacingLineRule());
	            					}
								}
	            			}
	            		}
	            		if(i == 33 && j == 2)
	            			System.out.println();
            			System.out.println(i +"." +j + " - " + xwpfRun.getText(0));
            	}
			}
            File outFile = new File("D:\\BM03_" + Calendar.getInstance().getTimeInMillis() + ".docx");
            os = new FileOutputStream(outFile);
            document.write(os);
            os.close();
            resource = new InputStreamResource(new FileInputStream(outFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(document != null)
				try {
					document.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"BM03.docx\"";
         
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);   
	}
	@RequestMapping(value = "/{assessment_id}/export02", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resource>  export02(@PathVariable long assessment_id,@AuthenticationPrincipal AuthenticatedUser user) {
		Assessment assessment = assessmentService.find(assessment_id);
		AssessmentResult result = assessmentService.findActiveResult(assessment_id, user.getId());

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"BM02.docx\"";
        File out = generate02(assessment, result);
        Resource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(out));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);  
	}
	private File generate02(Assessment assessment, AssessmentResult result) {
		XWPFDocument document = null;
		File file = null;
		OutputStream os = null;
		Resource resource = null;
		Map<String,Object> map = Utils.getMap(result);
		map.put("{org1}", assessment.getOrg1());
		map.put("{org2}", assessment.getOrg2());
		map.put("{ideaName}", assessment.getIdea().getText());
		map.put("{username}", result.getV_2_1());
		map.put("{level}", assessment.getLevel().getText());
		map.put("{member}", assessment.getIdea().getMembers());
		map.put("{day}", assessment.getSubmitedDate().get(Calendar.DAY_OF_MONTH) +"");
		map.put("{month}", assessment.getSubmitedDate().get(Calendar.MONTH) +"");
		map.put("{year}", assessment.getSubmitedDate().get(Calendar.YEAR) +"");
		map.put("{day1}", assessment.getCouncil().getStartDate().get(Calendar.DAY_OF_MONTH) +"");
		map.put("{month1}", assessment.getCouncil().getStartDate().get(Calendar.MONTH) +"");
		map.put("{year1}", assessment.getCouncil().getStartDate().get(Calendar.YEAR) +"");
		try {
			file = ResourceUtils.getFile("classpath:static/word/BM02.docx");
			document = new XWPFDocument(new FileInputStream(file));
            List<XWPFParagraph> paragraphs = document.getParagraphs();
    		CTOnOff on =CTOnOff.Factory.newInstance();
    		on.setVal(STOnOff1.ON);
    		CTOnOff off =CTOnOff.Factory.newInstance();
    		off.setVal(STOnOff1.OFF);
            List<XWPFTable> tables = document.getTables();
            List<XWPFParagraph> all = new ArrayList<>();
            for (int i = 0; i < tables.size(); i++) {

            	XWPFTable table = tables.get(i);
                List<XWPFTableRow> rows = table.getRows();
                for (int j = 0; j < rows.size(); j++) {
                	XWPFTableRow row = rows.get(j);
                    List<XWPFTableCell> cells = row.getTableCells();

                    for (int k = 0; k < cells.size(); k++) {
                    	XWPFTableCell cell = cells.get(k);
                    	all.addAll(cell.getParagraphs());
                    }
                	
                }
            }
            all.addAll(paragraphs);
            for (int i = 0; i < all.size(); i++) {
            	XWPFParagraph xwpfParagraph = all.get(i);
	            	List<XWPFRun> runs = xwpfParagraph.getRuns();
	            	for (int j = 0; j < runs.size(); j++) {
	            		XWPFRun xwpfRun = runs.get(j);
	            		//check
	            		if(xwpfRun.getCTR().getFldCharList().size() >0 && xwpfRun.getCTR().getFldCharList().get(0).getFfData() != null && xwpfRun.getCTR().getFldCharList().get(0).getFfData().getCheckBoxList().size() > 0 && xwpfRun.getCTR().getFldCharList().get(0).getFfData().getNameList().size() > 0) {
            				String name = xwpfRun.getCTR().getFldCharList().get(0).getFfData().getNameList().get(0).getVal();
	            			if(map.containsKey(name)) {
		                		xwpfRun.getCTR().getFldCharList().get(0).getFfData().getCheckBoxList().get(0).setChecked(on);
	            			}
	            		}
	            		//text
	            		String text= xwpfRun.getText(0) == null ? "" : xwpfRun.getText(0).trim();
	            		if(map.containsKey(text)) {
	            			if(map.get(text) == null || map.get(text).equals("")) {
	            				xwpfRun.setText("",0);
	            			}
	            			if(map.get(text) instanceof  String){
	            				xwpfRun.setText(String.valueOf(map.get(text)),0);
	            			}
	            			if(map.get(text) instanceof  List) {
	            				boolean first = true;
	            				for (Member member : (List<Member>)map.get(text)) {
	            					if(first) {
	            						xwpfRun.setText(member.getText(),0);
	            						first = false;
	            					}else {
		    	            			XmlCursor cursor = xwpfParagraph.getCTP().newCursor();
		    	            			cursor.toNextSibling();
		    	            			XWPFParagraph paragraph =  document.insertNewParagraph(cursor);
		            					XWPFRun run = paragraph.createRun();
		            					CTR ctr = (CTR)xwpfRun.getCTR().copy();
		            					run.getCTR().set(ctr);
	            						run.setText(member.getText(),0);
	            						paragraph.addRun(run);
	            						paragraph.setNumID(xwpfParagraph.getNumID());
	            						paragraph.setSpacingAfter(xwpfParagraph.getSpacingAfter());
	            						paragraph.setSpacingBefore(xwpfParagraph.getSpacingBefore());
	            						paragraph.setSpacingLineRule(xwpfParagraph.getSpacingLineRule());
	            					}
								}
	            			}
	            		}
            			System.out.println(i +"." +j + " - " + xwpfRun.getText(0));
            	}
			}
            File outFile = new File("D:\\BM02_" + Calendar.getInstance().getTimeInMillis() + ".docx");
            os = new FileOutputStream(outFile);
            document.write(os);
            os.close();
            return outFile;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(document != null)
				try {
					document.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return file;
	}
}
