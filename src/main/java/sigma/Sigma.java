package sigma;

import sigma.model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sigma {

	public static final String SUPER_USERNAME = "croteau.mike@gmail.com";
	public static final String SUPER_PASSWORD = "password";

	public static final String SUPER_ROLE  = "SUPER_ROLE";
	public static final String PUPIL_ROLE  = "PUPIL_ROLE";
	public static final String PROFESSOR_ROLE   = "PROFESSOR_ROLE";

	public static final String DATE_TIME = "yyyyMMddHHmm";
	public static final String DATE_FORMAT = "yyyyMMddHHmm";
	public static final String DATE_PRETTY = "HH:mmaa dd MMM";
	public static final String TIME_FORMAT = "hh:mm z";

	public static final String HEALTH_GROUP = "Sigma";
	public static final String VIDEO_REPO_KEY = "VideoRepo";
	public static final String PHONE_SERVICE_KEY = "SmsService";
	public static final int    REMINDER_JOBS_DURATION = 60;

	public static final String REMINDER_JOB1 = "Reminder Job";
	public static final String REMINDER_TRIGGER = "Trigger1";

	public static final String USER_MAINTENANCE = "users:";
	public static final String VIDEO_MAINTENANCE = "videos:";


	public static String getPhones(List<User> users){
		String numbers = "";
		int counter = 1;
		for(User user : users){
			numbers += "+1" + user.getPhone();
			if(counter < users.size())numbers += "<";
			counter++;
		}
		return numbers;
	}

	public static int getNumber(int max){
		Random r = new Random();
		return r.nextInt(max);
	}

	public static boolean containsSpecialCharacters(String str) {
		Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.find();
	}


	public static int getNumber(int max){
		Random r = new Random();
		return r.nextInt(max);
	}

	public static boolean containsSpecialCharacters(String str) {
		Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.find();
	}


	private static String getExtension(final String path) {
		String result = null;
		if (path != null) {
			result = "";
			if (path.lastIndexOf('.') != -1) {
				result = path.substring(path.lastIndexOf('.'));
				if (result.startsWith(".")) {
					result = result.substring(1);
				}
			}
		}
		return result;
	}

	public static String getString(int n) {
		String CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
		StringBuilder uuid = new StringBuilder();
		Random rnd = new Random();
		while (uuid.length() < n) {
			int index = (int) (rnd.nextFloat() * CHARS.length());
			uuid.append(CHARS.charAt(index));
		}
		return uuid.toString();
	}

	public static long getDate(){
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(BDo.DATE_FORMAT);
		String date = dtf.format(ldt);
		return Long.parseLong(date);
	}

	public static long getDateTimezone(String timezone){
		LocalDateTime ldt = LocalDateTime.now();
		ZoneId zone = ZoneId.systemDefault();
		ZoneOffset zoneOffset = zone.getRules().getOffset(ldt);
		ZonedDateTime zdt = ldt.atOffset(zoneOffset)
				.atZoneSameInstant(ZoneId.of(timezone));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(BDo.DATE_FORMAT);
		String date = dtf.format(zdt);
		return Long.parseLong(date);
	}

	public static long getDateTimezoneMins(int mins, String timezone){
		LocalDateTime ldt = LocalDateTime.now().plusMinutes(mins);
		ZoneId zone = ZoneId.systemDefault();
		ZoneOffset zoneOffset = zone.getRules().getOffset(ldt);
		ZonedDateTime zdt = ldt.atOffset(zoneOffset)
				.atZoneSameInstant(ZoneId.of(timezone));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(BDo.DATE_FORMAT);
		String date = dtf.format(zdt);
		return Long.parseLong(date);
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static String getPretty(Long date){
		String dateString = "";
		try {
			SimpleDateFormat parser = new SimpleDateFormat(BDo.DATE_FORMAT);
			Date d = parser.parse(Long.toString(date));

			SimpleDateFormat sdf2 = new SimpleDateFormat(BDo.DATE_PRETTY);
			dateString = sdf2.format(d);
		}catch(Exception ex){}
		return dateString;
	}

	public static String pad(String value, int places, String character){
		if(value.length() < places){
			value = character.concat(value);
			pad(value, places, character);
		}
		return value;
	}

}