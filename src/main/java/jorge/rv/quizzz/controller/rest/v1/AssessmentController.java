package jorge.rv.quizzz.controller.rest.v1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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

import jorge.rv.quizzz.controller.utils.Utils;
import jorge.rv.quizzz.model.Assessment;
import jorge.rv.quizzz.model.AssessmentResult;
import jorge.rv.quizzz.model.AuthenticatedUser;
import jorge.rv.quizzz.model.Member;
import jorge.rv.quizzz.model.support.Response;
import jorge.rv.quizzz.service.AssessmentService;

@RestController
@RequestMapping(AssessmentController.ROOT_MAPPING)
public class AssessmentController {

	public static final String ROOT_MAPPING = "/api/assessments";
	
	private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

	@Autowired
	private AssessmentService assessmentService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Page<Assessment> findAll(Pageable pageable,
			@RequestParam(required = false, defaultValue = "false") Boolean active) {
		
		if (active) {
			return assessmentService.findAllActive(pageable);
		} else {
			return assessmentService.findAll(pageable);
		}
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


	@RequestMapping(value = "/{assessment_id}/submit", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resource>  playQuiz(@PathVariable long assessment_id, @RequestBody Response resp,@AuthenticationPrincipal AuthenticatedUser user) {
		Assessment assessment = assessmentService.find(assessment_id);
		AssessmentResult result = new AssessmentResult(resp);
		result.setCreatedDate(Calendar.getInstance());
		result.setAssessment(assessment);
		if(user != null)
			result = assessmentService.save(result,user.getUser());
		else
			result = assessmentService.save(result);
		XWPFDocument document = null;
		File file = null;
		OutputStream os = null;
		Resource resource = null;
		Map<String,Object> map = Utils.getMap(resp);
		map.put("{ideaName}", assessment.getIdea().getText());
		map.put("{username}", result.getV_2_1());
		map.put("{level}", assessment.getLevel().getText());
		map.put("{member}", assessment.getIdea().getMembers());
		map.put("{day}", result.getCreatedDate().get(Calendar.DAY_OF_MONTH) +"");
		map.put("{month}", result.getCreatedDate().get(Calendar.MONTH) +"");
		map.put("{year}", result.getCreatedDate().get(Calendar.YEAR) +"");
		try {
			file = ResourceUtils.getFile("classpath:static/word/BM02.docx");
			document = new XWPFDocument(new FileInputStream(file));
            List<XWPFParagraph> paragraphs = document.getParagraphs();
    		CTOnOff on =CTOnOff.Factory.newInstance();
    		on.setVal(STOnOff1.ON);
    		CTOnOff off =CTOnOff.Factory.newInstance();
    		off.setVal(STOnOff1.OFF);
            for (int i = 0; i < paragraphs.size(); i++) {
            	XWPFParagraph xwpfParagraph = paragraphs.get(i);
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
	            		if(map.containsKey(xwpfRun.getText(0))) {
	            			if(map.get(xwpfRun.getText(0).trim()) == null) {
	            				xwpfRun.setText("",0);
	            			}
	            			if(map.get(xwpfRun.getText(0)) instanceof  String){
	            				xwpfRun.setText(String.valueOf(map.get(xwpfRun.getText(0))),0);
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
//	            		j++;
	                	if(i==8 && j ==1) {}
            			System.out.println(i +"." +j + " - " + xwpfRun.getText(0));
            	}
			}
            File outFile = new File("D:\\BM02.docx");
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
        String headerValue = "attachment; filename=\"BM02.docx\"";
         
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);   
	}

}
