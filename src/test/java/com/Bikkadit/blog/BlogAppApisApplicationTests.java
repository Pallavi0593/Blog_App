package com.Bikkadit.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Bikkadit.blog.Repositories.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {
       @Autowired
	private UserRepo userrepo;
	
@Test
    public void repotest()
    {
	String classname = this.userrepo.getClass().getName();
	
	String packageName = this.userrepo.getClass().getPackageName();
	
	System.out.println(classname);
	System.out.println( packageName);
}
}
