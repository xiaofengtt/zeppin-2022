package com.whaty.platform.standard.aicc.file;

import java.util.Map;


public class CRSData implements AiccFileModel{

    private Course course = new Course();

    private CourseBehavior courseBehavior = new CourseBehavior();

    private CourseDescription courseDes = new CourseDescription();

    public CRSData(){
    
    }
     
    public Course getCourse() {
        return course;
    }

    public CourseBehavior getCourseBehavior() {
        return courseBehavior;
    }

    public CourseDescription getCourseDes() {
        return courseDes;
    }
    
  

    public class Course {

        private String courseCreator;

        private String courseSystem;

        private String courseID;

        private String courseTitle;

        private String level;

        private String maxFieldsCST;

        private String maxFieldsORT;

        private String totalAus;

        private String totalBlocks;

        private String totalObjectives;
        
        private String totalComplexObjectives;

        private String version;

        public void setData(Map map){
            this.courseCreator = (String)map.get("course_creator");  
            this.courseSystem = (String)map.get("course_system");
            this.courseID = (String)map.get("course_id");
            this.courseTitle = (String)map.get("course_title");
            this.level = (String)map.get("level");
            this.maxFieldsCST = (String)map.get("max_fields_cst");
            this.maxFieldsORT = (String)map.get("max_fields_ort");
            this.totalAus = (String)map.get("total_aus");
            this.totalBlocks = (String)map.get("total_blocks");
            this.totalObjectives = (String)map.get("total_objectives");
            this.version = (String)map.get("version");

        }
        
        public String getCourseCreator() {
            return courseCreator;
        }

        public String getCourseID() {
            return courseID;
        }

        public String getCourseSystem() {
            return courseSystem;
        }

        public String getCourseTitle() {
          return courseTitle;
        }

        public String getLevel() {
            return level;
        }

        public String getMaxFieldsCST() {
        	if(maxFieldsCST!=null)
        		return maxFieldsCST;
        	else
        		return "1";
        }

        public String getMaxFieldsORT() {
        	if(maxFieldsORT!=null)
        		return maxFieldsORT;
        	else
        		return "1";
        	
        }

        public String getTotalAus() {
        	if(totalAus!=null)
        		return totalAus;
        	else
        		return "1";
        }

        public String getTotalBlocks() {
        	if(totalBlocks!=null)
        		return totalBlocks;
        	else
        		return "0";
        }

        public String getTotalObjectives() {
        	if(totalObjectives!=null)
        		return totalObjectives;
        	else
        		return "0";
        }

        public String getVersion() {
            return version;
        }

		public String getTotalComplexObjectives() {
			if(totalComplexObjectives!=null)
				return totalComplexObjectives;
			else
				return "0";
		}

		public void setTotalComplexObjectives(String totalComplexObjectives) {
			this.totalComplexObjectives = totalComplexObjectives;
		}

		public void setCourseCreator(String courseCreator) {
			this.courseCreator = courseCreator;
		}

		public void setCourseID(String courseID) {
			this.courseID = courseID;
		}

		public void setCourseSystem(String courseSystem) {
			this.courseSystem = courseSystem;
		}

		public void setCourseTitle(String courseTitle) {
			this.courseTitle = courseTitle;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public void setMaxFieldsCST(String maxFieldsCST) {
			this.maxFieldsCST = maxFieldsCST;
		}

		public void setMaxFieldsORT(String maxFieldsORT) {
			this.maxFieldsORT = maxFieldsORT;
		}

		public void setTotalAus(String totalAus) {
			this.totalAus = totalAus;
		}

		public void setTotalBlocks(String totalBlocks) {
			this.totalBlocks = totalBlocks;
		}

		public void setTotalObjectives(String totalObjectives) {
			this.totalObjectives = totalObjectives;
		}

		public void setVersion(String version) {
			this.version = version;
		}
    }

    public class CourseBehavior {

        private String maxNormal;

        public void setData(Map map){
            maxNormal = (String)map.get("max_normal");
        }
        
        public String getMaxNormal() {
            return maxNormal;
        }

		public void setMaxNormal(String maxNormal) {
			this.maxNormal = maxNormal;
		}
    }

    public class CourseDescription {

        private String courseDescription;

        
        public void setData(Map map){
            courseDescription = (String)map.get("coursedescription");     
           
        }
        
        public String getCourseDescription() {
        	 return courseDescription;
        }

		public void setCourseDescription(String courseDescription) {
			this.courseDescription = courseDescription;
		}
       
    }

	public String toStrData() {
		/*
		[Course]
		course_creator=
		course_id =
		course_system =
		course_title =
		level=
		max_fields_cst=
		max_fields_ort = 
		total_aus = 
		total_blocks = 
		total_objectives =
		total_complex_objectives =
		version =
		[Course_Behavior]
		max_normal =
		[Course_Description]
		
		 */
		
		StringBuffer sb=new StringBuffer();
		sb.append("[course]\n");
		sb.append("course_creator=");
		sb.append(this.getCourse().getCourseCreator());
		sb.append("\n");
		sb.append("course_id=");
		sb.append(this.getCourse().getCourseID());
		sb.append("\n");
		sb.append("course_system=");
		sb.append(this.getCourse().getCourseSystem());
		sb.append("\n");
		sb.append("course_title=");
		sb.append(this.getCourse().getCourseTitle());
		sb.append("\n");
		sb.append("level=");
		sb.append(this.getCourse().getLevel());
		sb.append("\n");
		sb.append("max_fields_cst=");
		sb.append(this.getCourse().getMaxFieldsCST());
		sb.append("\n");
		sb.append("max_fields_ort=");
		sb.append(this.getCourse().getMaxFieldsORT());
		sb.append("\n");
		sb.append("total_aus=");
		sb.append(this.getCourse().getTotalAus());
		sb.append("\n");
		sb.append("total_blocks=");
		sb.append(this.getCourse().getTotalBlocks());
		sb.append("\n");
		sb.append("total_objectives=");
		sb.append(this.getCourse().getTotalObjectives());
		sb.append("\n");
		sb.append("version=");
		sb.append(this.getCourse().getVersion());
		sb.append("\n");
		sb.append("[course_behavior]\n");
		sb.append("max_normal=");
		sb.append(this.getCourseBehavior().getMaxNormal());
		sb.append("\n");
		sb.append("[course_description]\n");
		sb.append(this.getCourseDes().getCourseDescription());
		sb.append("\n");
		return sb.toString();
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setCourseBehavior(CourseBehavior courseBehavior) {
		this.courseBehavior = courseBehavior;
	}

	public void setCourseDes(CourseDescription courseDes) {
		this.courseDes = courseDes;
	}
}