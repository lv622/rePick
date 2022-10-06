package com.gosari.repick_project.user;

import com.gosari.repick_project.exception.DataNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public SiteUser create(String username, String email, String password, String nickname, MultipartFile file)throws Exception {
        String profileImage = System.getProperty("user.dir") + "\\\\src\\\\main\\\\resources\\\\static\\\\photo";
        UUID uuid = UUID.randomUUID();
        String ImageName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(profileImage, ImageName);
        file.transferTo(saveFile);

        SiteUser user = new SiteUser();

//        user.getId();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        user.setImageName(ImageName);
        user.setProfileImage("/photo/"+ ImageName);

        this.userRepository.save(user);
        return user;
    }

    /*SiteUser를 조회할수있는 getUser메서드*/
    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("siteuser not found");
        }
    }


    //회원수정
    public SiteUser modify(SiteUser siteUser,String nickname, String email, MultipartFile file) throws Exception {
        String profileImage = System.getProperty("user.dir") + "\\\\src\\\\main\\\\resources\\\\static\\\\photo";
        UUID uuid = UUID.randomUUID();
        String ImageName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(profileImage, ImageName);
        file.transferTo(saveFile);

        siteUser.setImageName(ImageName);
        siteUser.setProfileImage("/photo/"+ImageName);

        siteUser.setNickname(nickname);
        siteUser.setEmail(email);

        this.userRepository.save(siteUser);
        return siteUser;
    }
}
