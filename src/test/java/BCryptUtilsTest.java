import org.glenlivet.core.security.BCryptUtils;
import org.junit.Test;
import org.springframework.util.Assert;


public class BCryptUtilsTest {
	
	@Test
	public void passTest(){
		
		String testPass = "uc4ntc4nt";
		
		String hash = BCryptUtils.hashPassword(testPass);
		System.out.println(hash);
		System.out.println(hash.length());
		boolean verified = BCryptUtils.checkPassword(testPass, hash);
		
		Assert.isTrue(verified);
		
		verified = BCryptUtils.checkPassword("uc4ntc5nt", hash);
		
		Assert.isTrue(!verified);
		
		String plain = "xdsvfawer23r2wfeq134e13r32e3e2413e31413e413r213r4213rd32rf23r32rxdsvfawer23r2wfeq134e13r32e3e2413e31413e413r213r4213rd32rf23r32r";
		String hash2 = BCryptUtils.hashPassword(plain);
		System.out.println(hash2);
		System.out.println(hash2.length() + " " + plain.length());
	}

}
