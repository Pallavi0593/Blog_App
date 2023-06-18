package com.Bikkadit.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Bikkadit.blog.Repositories.RoleRepo;
import com.Bikkadit.blog.config.Appconstants;
import com.Bikkadit.blog.entities.Roles;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo rolesrepo;

	public static void main(String[] args) {
		
		SpringApplication.run(BlogAppApisApplication.class, args);

		System.out.println("Blog Application Running");
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(this.passwordEncoder.encode("harShal05"));
		try {
			
		Roles role=new Roles(Appconstants.NORMAL_USER,"ROLE_NORAML");
		
		Roles role1=new Roles(Appconstants.ADMIN_USER,"ROLE_ADMIN");
		
		List<Roles> of = List.of(role,role1);
		
		List<Roles> saveAll = this.rolesrepo.saveAll(of);
		
		saveAll.forEach(r->System.out.println(r.getRoleName()));
		}catch (Exception e) {
			// TODO: handle exception
		}

	}

}
