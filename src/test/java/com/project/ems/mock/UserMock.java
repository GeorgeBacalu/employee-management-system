package com.project.ems.mock;

import com.project.ems.user.User;
import com.project.ems.user.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities1;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMock {

    public static List<User> getMockedUsers() {
        return List.of(getMockedUser1(), getMockedUser2(), getMockedUser3(), getMockedUser4(), getMockedUser5(), getMockedUser6(), getMockedUser7(), getMockedUser8(), getMockedUser9(),
              getMockedUser10(), getMockedUser11(), getMockedUser12(), getMockedUser13(), getMockedUser14(), getMockedUser15(), getMockedUser16(), getMockedUser17(), getMockedUser18(),
              getMockedUser19(), getMockedUser20(), getMockedUser21(), getMockedUser22(), getMockedUser23(), getMockedUser24(), getMockedUser25(), getMockedUser26(), getMockedUser27(),
              getMockedUser28(), getMockedUser29(), getMockedUser30(), getMockedUser31(), getMockedUser32(), getMockedUser33(), getMockedUser34(), getMockedUser35(), getMockedUser36(),
              getMockedUser37(), getMockedUser38(), getMockedUser39(), getMockedUser40(), getMockedUser41(), getMockedUser42(), getMockedUser43(), getMockedUser44(), getMockedUser45(),
              getMockedUser46(), getMockedUser47(), getMockedUser48(), getMockedUser49(), getMockedUser50(), getMockedUser51(), getMockedUser52(), getMockedUser53(), getMockedUser54(),
              getMockedUser55(), getMockedUser56(), getMockedUser57(), getMockedUser58(), getMockedUser59(), getMockedUser60(), getMockedUser61(), getMockedUser62(), getMockedUser63(),
              getMockedUser64(), getMockedUser65(), getMockedUser66(), getMockedUser67(), getMockedUser68(), getMockedUser69(), getMockedUser70(), getMockedUser71(), getMockedUser72());
    }

    public static List<UserDto> getMockedUserDtos() {
        return List.of(getMockedUserDto1(), getMockedUserDto2(), getMockedUserDto3(), getMockedUserDto4(), getMockedUserDto5(), getMockedUserDto6(), getMockedUserDto7(), getMockedUserDto8(), getMockedUserDto9(),
              getMockedUserDto10(), getMockedUserDto11(), getMockedUserDto12(), getMockedUserDto13(), getMockedUserDto14(), getMockedUserDto15(), getMockedUserDto16(), getMockedUserDto17(), getMockedUserDto18(),
              getMockedUserDto19(), getMockedUserDto20(), getMockedUserDto21(), getMockedUserDto22(), getMockedUserDto23(), getMockedUserDto24(), getMockedUserDto25(), getMockedUserDto26(), getMockedUserDto27(),
              getMockedUserDto28(), getMockedUserDto29(), getMockedUserDto30(), getMockedUserDto31(), getMockedUserDto32(), getMockedUserDto33(), getMockedUserDto34(), getMockedUserDto35(), getMockedUserDto36(),
              getMockedUserDto37(), getMockedUserDto38(), getMockedUserDto39(), getMockedUserDto40(), getMockedUserDto41(), getMockedUserDto42(), getMockedUserDto43(), getMockedUserDto44(), getMockedUserDto45(),
              getMockedUserDto46(), getMockedUserDto47(), getMockedUserDto48(), getMockedUserDto49(), getMockedUserDto50(), getMockedUserDto51(), getMockedUserDto52(), getMockedUserDto53(), getMockedUserDto54(),
              getMockedUserDto55(), getMockedUserDto56(), getMockedUserDto57(), getMockedUserDto58(), getMockedUserDto59(), getMockedUserDto60(), getMockedUserDto61(), getMockedUserDto62(), getMockedUserDto63(),
              getMockedUserDto64(), getMockedUserDto65(), getMockedUserDto66(), getMockedUserDto67(), getMockedUserDto68(), getMockedUserDto69(), getMockedUserDto70(), getMockedUserDto71(), getMockedUserDto72());
    }

    public static List<User> getMockedActiveUsers() {
        return getMockedUsers().stream().filter(User::getIsActive).toList();
    }

    public static List<UserDto> getMockedActiveUserDtos() {
        return getMockedUserDtos().stream().filter(UserDto::getIsActive).toList();
    }

    public static User getMockedUser1() {
        return User.builder()
              .id(1)
              .name("John Doe")
              .email("john.doe@example.com")
              .password("#John_Doe_Password0")
              .mobile("+40721543701")
              .address("123 Main St, Boston, USA")
              .birthday(LocalDate.of(1980, 2, 15))
              .role(getMockedRole2())
              .authorities(getMockedAuthorities())
              .isActive(true)
              .build();
    }

    public static User getMockedUser2() {
        return User.builder()
              .id(2)
              .name("Jane Smith")
              .email("jane.smith@example.com")
              .password("#Jane_Smith_Password0")
              .mobile("+40756321802")
              .address("456 Oak St, London, UK")
              .birthday(LocalDate.of(1982, 7, 10))
              .role(getMockedRole2())
              .authorities(getMockedAuthorities())
              .isActive(true)
              .build();
    }

    public static User getMockedUser3() {
        return User.builder()
              .id(3)
              .name("Michael Johnson")
              .email("michael.johnson@example.com")
              .password("#Michael_Johnson_Password0")
              .mobile("+40789712303")
              .address("789 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1990, 11, 20))
              .role(getMockedRole2())
              .authorities(getMockedAuthorities())
              .isActive(true)
              .build();
    }

    public static User getMockedUser4() {
        return User.builder()
              .id(4)
              .name("Laura Brown")
              .email("laura.brown@example.com")
              .password("#Laura_Brown_Password0")
              .mobile("+40734289604")
              .address("333 Elm St, Paris, France")
              .birthday(LocalDate.of(1985, 8, 25))
              .role(getMockedRole2())
              .authorities(getMockedAuthorities())
              .isActive(true)
              .build();
    }

    public static User getMockedUser5() {
        return User.builder()
              .id(5)
              .name("Robert Davis")
              .email("robert.davis@example.com")
              .password("#Robert_Davis_Password0")
              .mobile("+40754321805")
              .address("555 Oak St, Berlin, Germany")
              .birthday(LocalDate.of(1988, 5, 12))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser6() {
        return User.builder()
              .id(6)
              .name("Emily Wilson")
              .email("emily.wilson@example.com")
              .password("#Emily_Wilson_Password0")
              .mobile("+40789012606")
              .address("777 Pine St, Sydney, Australia")
              .birthday(LocalDate.of(1995, 9, 8))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser7() {
        return User.builder()
              .id(7)
              .name("Michaela Taylor")
              .email("michaela.taylor@example.com")
              .password("#Michaela_Taylor_Password0")
              .mobile("+40723145607")
              .address("999 Elm St, Rome, Italy")
              .birthday(LocalDate.of(1983, 12, 7))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser8() {
        return User.builder()
              .id(8)
              .name("David Anderson")
              .email("david.anderson@example.com")
              .password("#David_Anderson_Password0")
              .mobile("+40787654308")
              .address("111 Oak St, Moscow, Russia")
              .birthday(LocalDate.of(1992, 4, 23))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser9() {
        return User.builder()
              .id(9)
              .name("Sophia Garcia")
              .email("sophia.garcia@example.com")
              .password("#Sophia_Garcia_Password0")
              .mobile("+40754321809")
              .address("333 Pine St, Athens, Greece")
              .birthday(LocalDate.of(1998, 7, 30))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser10() {
        return User.builder()
              .id(10)
              .name("Joseph Wilson")
              .email("joseph.wilson@example.com")
              .password("#Joseph_Wilson_Password0")
              .mobile("+40789012610")
              .address("555 Elm St, Madrid, Spain")
              .birthday(LocalDate.of(1991, 3, 14))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser11() {
        return User.builder()
              .id(11)
              .name("Olivia Martinez")
              .email("olivia.martinez@example.com")
              .password("#Olivia_Martinez_Password0")
              .mobile("+40723145611")
              .address("777 Oak St, Tokyo, Japan")
              .birthday(LocalDate.of(1999, 10, 17))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser12() {
        return User.builder()
              .id(12)
              .name("Daniel Thompson")
              .email("daniel.thompson@example.com")
              .password("#Daniel_Thompson_Password0")
              .mobile("+40787654312")
              .address("999 Elm St, Seoul, South Korea")
              .birthday(LocalDate.of(1994, 6, 9))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser13() {
        return User.builder()
              .id(13)
              .name("Emma Thompson")
              .email("emma.thompson@example.com")
              .password("#Emma_Thompson_Password0")
              .mobile("+40754321813")
              .address("111 Pine St, Beijing, China")
              .birthday(LocalDate.of(2000, 12, 22))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser14() {
        return User.builder()
              .id(14)
              .name("Liam Brown")
              .email("liam.brown@example.com")
              .password("#Liam_Brown_Password0")
              .mobile("+40789012614")
              .address("333 Oak St, Cape Town, South Africa")
              .birthday(LocalDate.of(1997, 9, 4))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser15() {
        return User.builder()
              .id(15)
              .name("Olivia Wilson")
              .email("olivia.wilson@example.com")
              .password("#Olivia_Wilson_Password0")
              .mobile("+40723145615")
              .address("555 Elm St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(2001, 4, 7))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser16() {
        return User.builder()
              .id(16)
              .name("Noah Taylor")
              .email("noah.taylor@example.com")
              .password("#Noah_Taylor_Password0")
              .mobile("+40787654316")
              .address("777 Pine St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1996, 11, 19))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser17() {
        return User.builder()
              .id(17)
              .name("Ava Johnson")
              .email("ava.johnson@example.com")
              .password("#Ava_Johnson_Password0")
              .mobile("+40754321817")
              .address("999 Oak St, Mexico City, Mexico")
              .birthday(LocalDate.of(2002, 6, 2))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser18() {
        return User.builder()
              .id(18)
              .name("William Davis")
              .email("william.davis@example.com")
              .password("#William_Davis_Password0")
              .mobile("+40789012618")
              .address("111 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(1993, 2, 25))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser19() {
        return User.builder()
              .id(19)
              .name("Sophia Martinez")
              .email("sophia.martinez@example.com")
              .password("#Sophia_Martinez_Password0")
              .mobile("+40723145619")
              .address("333 Oak St, Paris, France")
              .birthday(LocalDate.of(2003, 9, 8))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser20() {
        return User.builder()
              .id(20)
              .name("Isabella Anderson")
              .email("isabella.anderson@example.com")
              .password("#Isabella_Anderson_Password0")
              .mobile("+40787654320")
              .address("555 Elm St, London, UK")
              .birthday(LocalDate.of(1999, 6, 22))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser21() {
        return User.builder()
              .id(21)
              .name("Mason Thompson")
              .email("mason.thompson@example.com")
              .password("#Mason_Thompson_Password0")
              .mobile("+40754321821")
              .address("777 Pine St, Berlin, Germany")
              .birthday(LocalDate.of(2002, 12, 20))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser22() {
        return User.builder()
              .id(22)
              .name("Charlotte Thompson")
              .email("charlotte.thompson@example.com")
              .password("#Charlotte_Thompson_Password0")
              .mobile("+40789012622")
              .address("999 Oak St, Moscow, Russia")
              .birthday(LocalDate.of(1998, 10, 18))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser23() {
        return User.builder()
              .id(23)
              .name("Elijah Smith")
              .email("elijah.smith@example.com")
              .password("#Elijah_Smith_Password0")
              .mobile("+40723145623")
              .address("111 Elm St, Athens, Greece")
              .birthday(LocalDate.of(2003, 5, 3))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser24() {
        return User.builder()
              .id(24)
              .name("Amelia Johnson")
              .email("amelia.johnson@example.com")
              .password("#Amelia_Johnson_Password0")
              .mobile("+40787654324")
              .address("333 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1998, 12, 14))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser25() {
        return User.builder()
              .id(25)
              .name("Harper Wilson")
              .email("harper.wilson@example.com")
              .password("#Harper_Wilson_Password0")
              .mobile("+40754321825")
              .address("555 Oak St, Tokyo, Japan")
              .birthday(LocalDate.of(2001, 7, 27))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser26() {
        return User.builder()
              .id(26)
              .name("Benjamin Thompson")
              .email("benjamin.thompson@example.com")
              .password("#Benjamin_Thompson_Password0")
              .mobile("+40789012626")
              .address("777 Elm St, Seoul, South Korea")
              .birthday(LocalDate.of(2001, 2, 9))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser27() {
        return User.builder()
              .id(27)
              .name("Liam Thompson")
              .email("liam.thompson@example.com")
              .password("#Liam_Thompson_Password0")
              .mobile("+40723145627")
              .address("999 Oak St, Beijing, China")
              .birthday(LocalDate.of(2000, 9, 23))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser28() {
        return User.builder()
              .id(28)
              .name("Grace Martinez")
              .email("grace.martinez@example.com")
              .password("#Grace_Martinez_Password0")
              .mobile("+40787654328")
              .address("111 Elm St, Cape Town, South Africa")
              .birthday(LocalDate.of(2002, 6, 6))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser29() {
        return User.builder()
              .id(29)
              .name("Isabella White")
              .email("isabella.white@example.com")
              .password("#Isabella_White_Password0")
              .mobile("+40754321829")
              .address("333 Pine St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(2002, 1, 19))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser30() {
        return User.builder()
              .id(30)
              .name("Logan Thompson")
              .email("logan.thompson@example.com")
              .password("#Logan_Thompson_Password0")
              .mobile("+40789012630")
              .address("555 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(2003, 7, 2))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser31() {
        return User.builder()
              .id(31)
              .name("Evelyn Brown")
              .email("evelyn.brown@example.com")
              .password("#Evelyn_Brown_Password0")
              .mobile("+40723145631")
              .address("777 Oak St, Mexico City, Mexico")
              .birthday(LocalDate.of(1999, 3, 16))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser32() {
        return User.builder()
              .id(32)
              .name("Henry Davis")
              .email("henry.davis@example.com")
              .password("#Henry_Davis_Password0")
              .mobile("+40787654332")
              .address("999 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(1998, 10, 29))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser33() {
        return User.builder()
              .id(33)
              .name("Sofia Smith")
              .email("sofia.smith@example.com")
              .password("#Sofia_Smith_Password0")
              .mobile("+40754321833")
              .address("111 Oak St, Paris, France")
              .birthday(LocalDate.of(2000, 6, 11))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser34() {
        return User.builder()
              .id(34)
              .name("Jack Wilson")
              .email("jack.wilson@example.com")
              .password("#Jack_Wilson_Password0")
              .mobile("+40789012634")
              .address("333 Elm St, London, UK")
              .birthday(LocalDate.of(1997, 1, 24))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser35() {
        return User.builder()
              .id(35)
              .name("Emily Anderson")
              .email("emily.anderson@example.com")
              .password("#Emily_Anderson_Password0")
              .mobile("+40723145635")
              .address("555 Pine St, Berlin, Germany")
              .birthday(LocalDate.of(1995, 8, 6))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser36() {
        return User.builder()
              .id(36)
              .name("Adrian Thompson")
              .email("adrian.thompson@example.com")
              .password("#Adrian_Thompson_Password0")
              .mobile("+40787654336")
              .address("777 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1996, 3, 20))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser37() {
        return User.builder()
              .id(37)
              .name("Abigail Johnson")
              .email("abigail.johnson@example.com")
              .password("#Abigail_Johnson_Password0")
              .mobile("+40754321837")
              .address("999 Oak St, Athens, Greece")
              .birthday(LocalDate.of(2000, 10, 2))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser38() {
        return User.builder()
              .id(38)
              .name("Michael Davis")
              .email("michael.davis@example.com")
              .password("#Michael_Davis_Password0")
              .mobile("+40789012638")
              .address("111 Oak St, Madrid, Spain")
              .birthday(LocalDate.of(1994, 5, 16))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser39() {
        return User.builder()
              .id(39)
              .name("Mia Wilson")
              .email("mia.wilson@example.com")
              .password("#Mia_Wilson_Password0")
              .mobile("+40723145639")
              .address("333 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1990, 12, 29))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser40() {
        return User.builder()
              .id(40)
              .name("James Lee")
              .email("james.lee@example.com")
              .password("#James_Lee_Password0")
              .mobile("+40787654340")
              .address("555 Pine St, Seoul, South Korea")
              .birthday(LocalDate.of(1991, 8, 11))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser41() {
        return User.builder()
              .id(41)
              .name("Maria Thompson")
              .email("maria.thompson@example.com")
              .password("#Maria_Thompson_Password0")
              .mobile("+40754321841")
              .address("777 Elm St, Beijing, China")
              .birthday(LocalDate.of(1993, 3, 24))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser42() {
        return User.builder()
              .id(42)
              .name("Ethan Smith")
              .email("ethan.smith@example.com")
              .password("#Ethan_Smith_Password0")
              .mobile("+40789012642")
              .address("999 Oak St, Cape Town, South Africa")
              .birthday(LocalDate.of(1989, 11, 6))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser43() {
        return User.builder()
              .id(43)
              .name("Olivia Smith")
              .email("olivia.smith@example.com")
              .password("#Olivia_Smith_Password0")
              .mobile("+40723145643")
              .address("111 Elm St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(1994, 6, 19))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser44() {
        return User.builder()
              .id(44)
              .name("Emily Davis")
              .email("emily.davis@example.com")
              .password("#Emily_Davis_Password0")
              .mobile("+40787654344")
              .address("333 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1998, 1, 1))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser45() {
        return User.builder()
              .id(45)
              .name("Henry Wilson")
              .email("henry.wilson@example.com")
              .password("#Henry_Wilson_Password0")
              .mobile("+40754321845")
              .address("555 Pine St, Mexico City, Mexico")
              .birthday(LocalDate.of(2001, 8, 14))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser46() {
        return User.builder()
              .id(46)
              .name("Scarlett Thompson")
              .email("scarlett.thompson@example.com")
              .password("#Scarlett_Thompson_Password0")
              .mobile("+40789012646")
              .address("777 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(2002, 3, 28))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser47() {
        return User.builder()
              .id(47)
              .name("Jacob Brown")
              .email("jacob.brown@example.com")
              .password("#Jacob_Brown_Password0")
              .mobile("+40723145647")
              .address("999 Pine St, Paris, France")
              .birthday(LocalDate.of(1999, 11, 10))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser48() {
        return User.builder()
              .id(48)
              .name("Ava Smith")
              .email("ava.smith@example.com")
              .password("#Ava_Smith_Password0")
              .mobile("+40787654348")
              .address("111 Pine St, London, UK")
              .birthday(LocalDate.of(1986, 6, 23))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser49() {
        return User.builder()
              .id(49)
              .name("Oliver Johnson")
              .email("oliver.johnson@example.com")
              .password("#Oliver_Johnson_Password0")
              .mobile("+40754321849")
              .address("333 Elm St, Berlin, Germany")
              .birthday(LocalDate.of(1988, 2, 5))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser50() {
        return User.builder()
              .id(50)
              .name("Sophia Wilson")
              .email("sophia.wilson@example.com")
              .password("#Sophia_Wilson_Password0")
              .mobile("+40789012650")
              .address("555 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1994, 9, 19))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser51() {
        return User.builder()
              .id(51)
              .name("William Harris")
              .email("william.harris@example.com")
              .password("#William_Harris_Password0")
              .mobile("+40723145651")
              .address("777 Pine St, Athens, Greece")
              .birthday(LocalDate.of(1996, 4, 3))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser52() {
        return User.builder()
              .id(52)
              .name("Mia Johnson")
              .email("mia.johnson@example.com")
              .password("#Mia_Johnson_Password0")
              .mobile("+40787654352")
              .address("999 Oak St, Madrid, Spain")
              .birthday(LocalDate.of(1998, 11, 16))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser53() {
        return User.builder()
              .id(53)
              .name("Jacob Lee")
              .email("jacob.lee@example.com")
              .password("#Jacob_Lee_Password0")
              .mobile("+40754321853")
              .address("111 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1997, 6, 29))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser54() {
        return User.builder()
              .id(54)
              .name("Charlotte Brown")
              .email("charlotte.brown@example.com")
              .password("#Charlotte_Brown_Password0")
              .mobile("+40789012654")
              .address("333 Pine St, Seoul, South Korea")
              .birthday(LocalDate.of(2000, 2, 12))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser55() {
        return User.builder()
              .id(55)
              .name("Ethan Johnson")
              .email("ethan.johnson@example.com")
              .password("#Ethan_Johnson_Password0")
              .mobile("+40723145655")
              .address("555 Elm St, Beijing, China")
              .birthday(LocalDate.of(1998, 9, 25))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser56() {
        return User.builder()
              .id(56)
              .name("Sophia Davis")
              .email("sophia.davis@example.com")
              .password("#Sophia_Davis_Password0")
              .mobile("+40787654356")
              .address("777 Elm St, Cape Town, South Africa")
              .birthday(LocalDate.of(2001, 7, 9))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser57() {
        return User.builder()
              .id(57)
              .name("Jacob Thompson")
              .email("jacob.thompson@example.com")
              .password("#Jacob_Thompson_Password0")
              .mobile("+40754321857")
              .address("999 Pine St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(1996, 2, 22))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser58() {
        return User.builder()
              .id(58)
              .name("Liam Wilson")
              .email("liam.wilson@example.com")
              .password("#Liam_Wilson_Password0")
              .mobile("+40789012658")
              .address("111 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1995, 10, 7))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser59() {
        return User.builder()
              .id(59)
              .name("Ava Brown")
              .email("ava.brown@example.com")
              .password("#Ava_Brown_Password0")
              .mobile("+40723145659")
              .address("333 Elm St, Mexico City, Mexico")
              .birthday(LocalDate.of(1998, 7, 21))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser60() {
        return User.builder()
              .id(60)
              .name("Scarlett Wilson")
              .email("scarlett.wilson@example.com")
              .password("#Scarlett_Wilson_Password0")
              .mobile("+40787654360")
              .address("555 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(2001, 5, 5))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser61() {
        return User.builder()
              .id(61)
              .name("Noah Martinez")
              .email("noah.martinez@example.com")
              .password("#Noah_Martinez_Password0")
              .mobile("+40754321861")
              .address("777 Pine St, Paris, France")
              .birthday(LocalDate.of(1999, 12, 17))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser62() {
        return User.builder()
              .id(62)
              .name("Mia Anderson")
              .email("mia.anderson@example.com")
              .password("#Mia_Anderson_Password0")
              .mobile("+40789012662")
              .address("999 Oak St, London, UK")
              .birthday(LocalDate.of(2002, 9, 30))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser63() {
        return User.builder()
              .id(63)
              .name("Grace Lee")
              .email("grace.lee@example.com")
              .password("#Grace_Lee_Password0")
              .mobile("+40723145663")
              .address("111 Elm St, Berlin, Germany")
              .birthday(LocalDate.of(1996, 5, 14))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser64() {
        return User.builder()
              .id(64)
              .name("Benjamin Taylor")
              .email("benjamin.taylor@example.com")
              .password("#Benjamin_Taylor_Password0")
              .mobile("+40787654364")
              .address("333 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(2002, 12, 27))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser65() {
        return User.builder()
              .id(65)
              .name("Elijah Roberts")
              .email("elijah.roberts@example.com")
              .password("#Elijah_Roberts_Password0")
              .mobile("+40754321865")
              .address("555 Pine St, Sydney, Australia")
              .birthday(LocalDate.of(2003, 1, 27))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser66() {
        return User.builder()
              .id(66)
              .name("Amelia Walker")
              .email("amelia.walker@example.com")
              .password("#Amelia_Walker_Password0")
              .mobile("+40789012666")
              .address("777 Oak St, Rome, Italy")
              .birthday(LocalDate.of(1992, 8, 12))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser67() {
        return User.builder()
              .id(67)
              .name("Daniel Green")
              .email("daniel.green@example.com")
              .password("#Daniel_Green_Password0")
              .mobile("+40723145667")
              .address("999 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1996, 3, 27))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser68() {
        return User.builder()
              .id(68)
              .name("Liam Hall")
              .email("liam.hall@example.com")
              .password("#Liam_Hall_Password0")
              .mobile("+40787654368")
              .address("111 Oak St, Athens, Greece")
              .birthday(LocalDate.of(1998, 11, 9))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser69() {
        return User.builder()
              .id(69)
              .name("Sophia Young")
              .email("sophia.young@example.com")
              .password("#Sophia_Young_Password0")
              .mobile("+40754321869")
              .address("333 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1995, 6, 23))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .build();
    }

    public static User getMockedUser70() {
        return User.builder()
              .id(70)
              .name("Noah Clark")
              .email("noah.clark@example.com")
              .password("#Noah_Clark_Password0")
              .mobile("+40789012670")
              .address("555 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1997, 2, 5))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser71() {
        return User.builder()
              .id(71)
              .name("Olivia Hill")
              .email("olivia.hill@example.com")
              .password("#Olivia_Hill_Password0")
              .mobile("+40723145671")
              .address("777 Oak St, Seoul, South Korea")
              .birthday(LocalDate.of(2001, 9, 20))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static User getMockedUser72() {
        return User.builder()
              .id(72)
              .name("Michaela Allen")
              .email("michaela.allen@example.com")
              .password("#Michaela_Allen_Password0")
              .mobile("+40787654372")
              .address("999 Pine St, Beijing, China")
              .birthday(LocalDate.of(1998, 5, 6))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto1() {
        return UserDto.builder()
              .id(1)
              .name("John Doe")
              .email("john.doe@example.com")
              .password("#John_Doe_Password0")
              .mobile("+40721543701")
              .address("123 Main St, Boston, USA")
              .birthday(LocalDate.of(1980, 2, 15))
              .roleId(2)
              .authoritiesIds(List.of(1, 2, 3, 4))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto2() {
        return UserDto.builder()
              .id(2)
              .name("Jane Smith")
              .email("jane.smith@example.com")
              .password("#Jane_Smith_Password0")
              .mobile("+40756321802")
              .address("456 Oak St, London, UK")
              .birthday(LocalDate.of(1982, 7, 10))
              .roleId(2)
              .authoritiesIds(List.of(1, 2, 3, 4))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto3() {
        return UserDto.builder()
              .id(3)
              .name("Michael Johnson")
              .email("michael.johnson@example.com")
              .password("#Michael_Johnson_Password0")
              .mobile("+40789712303")
              .address("789 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1990, 11, 20))
              .roleId(2)
              .authoritiesIds(List.of(1, 2, 3, 4))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto4() {
        return UserDto.builder()
              .id(4)
              .name("Laura Brown")
              .email("laura.brown@example.com")
              .password("#Laura_Brown_Password0")
              .mobile("+40734289604")
              .address("333 Elm St, Paris, France")
              .birthday(LocalDate.of(1985, 8, 25))
              .roleId(2)
              .authoritiesIds(List.of(1, 2, 3, 4))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto5() {
        return UserDto.builder()
              .id(5)
              .name("Robert Davis")
              .email("robert.davis@example.com")
              .password("#Robert_Davis_Password0")
              .mobile("+40754321805")
              .address("555 Oak St, Berlin, Germany")
              .birthday(LocalDate.of(1988, 5, 12))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto6() {
        return UserDto.builder()
              .id(6)
              .name("Emily Wilson")
              .email("emily.wilson@example.com")
              .password("#Emily_Wilson_Password0")
              .mobile("+40789012606")
              .address("777 Pine St, Sydney, Australia")
              .birthday(LocalDate.of(1995, 9, 8))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto7() {
        return UserDto.builder()
              .id(7)
              .name("Michaela Taylor")
              .email("michaela.taylor@example.com")
              .password("#Michaela_Taylor_Password0")
              .mobile("+40723145607")
              .address("999 Elm St, Rome, Italy")
              .birthday(LocalDate.of(1983, 12, 7))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto8() {
        return UserDto.builder()
              .id(8)
              .name("David Anderson")
              .email("david.anderson@example.com")
              .password("#David_Anderson_Password0")
              .mobile("+40787654308")
              .address("111 Oak St, Moscow, Russia")
              .birthday(LocalDate.of(1992, 4, 23))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto9() {
        return UserDto.builder()
              .id(9)
              .name("Sophia Garcia")
              .email("sophia.garcia@example.com")
              .password("#Sophia_Garcia_Password0")
              .mobile("+40754321809")
              .address("333 Pine St, Athens, Greece")
              .birthday(LocalDate.of(1998, 7, 30))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto10() {
        return UserDto.builder()
              .id(10)
              .name("Joseph Wilson")
              .email("joseph.wilson@example.com")
              .password("#Joseph_Wilson_Password0")
              .mobile("+40789012610")
              .address("555 Elm St, Madrid, Spain")
              .birthday(LocalDate.of(1991, 3, 14))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto11() {
        return UserDto.builder()
              .id(11)
              .name("Olivia Martinez")
              .email("olivia.martinez@example.com")
              .password("#Olivia_Martinez_Password0")
              .mobile("+40723145611")
              .address("777 Oak St, Tokyo, Japan")
              .birthday(LocalDate.of(1999, 10, 17))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto12() {
        return UserDto.builder()
              .id(12)
              .name("Daniel Thompson")
              .email("daniel.thompson@example.com")
              .password("#Daniel_Thompson_Password0")
              .mobile("+40787654312")
              .address("999 Elm St, Seoul, South Korea")
              .birthday(LocalDate.of(1994, 6, 9))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto13() {
        return UserDto.builder()
              .id(13)
              .name("Emma Thompson")
              .email("emma.thompson@example.com")
              .password("#Emma_Thompson_Password0")
              .mobile("+40754321813")
              .address("111 Pine St, Beijing, China")
              .birthday(LocalDate.of(2000, 12, 22))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto14() {
        return UserDto.builder()
              .id(14)
              .name("Liam Brown")
              .email("liam.brown@example.com")
              .password("#Liam_Brown_Password0")
              .mobile("+40789012614")
              .address("333 Oak St, Cape Town, South Africa")
              .birthday(LocalDate.of(1997, 9, 4))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto15() {
        return UserDto.builder()
              .id(15)
              .name("Olivia Wilson")
              .email("olivia.wilson@example.com")
              .password("#Olivia_Wilson_Password0")
              .mobile("+40723145615")
              .address("555 Elm St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(2001, 4, 7))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto16() {
        return UserDto.builder()
              .id(16)
              .name("Noah Taylor")
              .email("noah.taylor@example.com")
              .password("#Noah_Taylor_Password0")
              .mobile("+40787654316")
              .address("777 Pine St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1996, 11, 19))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto17() {
        return UserDto.builder()
              .id(17)
              .name("Ava Johnson")
              .email("ava.johnson@example.com")
              .password("#Ava_Johnson_Password0")
              .mobile("+40754321817")
              .address("999 Oak St, Mexico City, Mexico")
              .birthday(LocalDate.of(2002, 6, 2))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto18() {
        return UserDto.builder()
              .id(18)
              .name("William Davis")
              .email("william.davis@example.com")
              .password("#William_Davis_Password0")
              .mobile("+40789012618")
              .address("111 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(1993, 2, 25))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto19() {
        return UserDto.builder()
              .id(19)
              .name("Sophia Martinez")
              .email("sophia.martinez@example.com")
              .password("#Sophia_Martinez_Password0")
              .mobile("+40723145619")
              .address("333 Oak St, Paris, France")
              .birthday(LocalDate.of(2003, 9, 8))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto20() {
        return UserDto.builder()
              .id(20)
              .name("Isabella Anderson")
              .email("isabella.anderson@example.com")
              .password("#Isabella_Anderson_Password0")
              .mobile("+40787654320")
              .address("555 Elm St, London, UK")
              .birthday(LocalDate.of(1999, 6, 22))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto21() {
        return UserDto.builder()
              .id(21)
              .name("Mason Thompson")
              .email("mason.thompson@example.com")
              .password("#Mason_Thompson_Password0")
              .mobile("+40754321821")
              .address("777 Pine St, Berlin, Germany")
              .birthday(LocalDate.of(2002, 12, 20))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto22() {
        return UserDto.builder()
              .id(22)
              .name("Charlotte Thompson")
              .email("charlotte.thompson@example.com")
              .password("#Charlotte_Thompson_Password0")
              .mobile("+40789012622")
              .address("999 Oak St, Moscow, Russia")
              .birthday(LocalDate.of(1998, 10, 18))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto23() {
        return UserDto.builder()
              .id(23)
              .name("Elijah Smith")
              .email("elijah.smith@example.com")
              .password("#Elijah_Smith_Password0")
              .mobile("+40723145623")
              .address("111 Elm St, Athens, Greece")
              .birthday(LocalDate.of(2003, 5, 3))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto24() {
        return UserDto.builder()
              .id(24)
              .name("Amelia Johnson")
              .email("amelia.johnson@example.com")
              .password("#Amelia_Johnson_Password0")
              .mobile("+40787654324")
              .address("333 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1998, 12, 14))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto25() {
        return UserDto.builder()
              .id(25)
              .name("Harper Wilson")
              .email("harper.wilson@example.com")
              .password("#Harper_Wilson_Password0")
              .mobile("+40754321825")
              .address("555 Oak St, Tokyo, Japan")
              .birthday(LocalDate.of(2001, 7, 27))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto26() {
        return UserDto.builder()
              .id(26)
              .name("Benjamin Thompson")
              .email("benjamin.thompson@example.com")
              .password("#Benjamin_Thompson_Password0")
              .mobile("+40789012626")
              .address("777 Elm St, Seoul, South Korea")
              .birthday(LocalDate.of(2001, 2, 9))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto27() {
        return UserDto.builder()
              .id(27)
              .name("Liam Thompson")
              .email("liam.thompson@example.com")
              .password("#Liam_Thompson_Password0")
              .mobile("+40723145627")
              .address("999 Oak St, Beijing, China")
              .birthday(LocalDate.of(2000, 9, 23))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto28() {
        return UserDto.builder()
              .id(28)
              .name("Grace Martinez")
              .email("grace.martinez@example.com")
              .password("#Grace_Martinez_Password0")
              .mobile("+40787654328")
              .address("111 Elm St, Cape Town, South Africa")
              .birthday(LocalDate.of(2002, 6, 6))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto29() {
        return UserDto.builder()
              .id(29)
              .name("Isabella White")
              .email("isabella.white@example.com")
              .password("#Isabella_White_Password0")
              .mobile("+40754321829")
              .address("333 Pine St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(2002, 1, 19))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto30() {
        return UserDto.builder()
              .id(30)
              .name("Logan Thompson")
              .email("logan.thompson@example.com")
              .password("#Logan_Thompson_Password0")
              .mobile("+40789012630")
              .address("555 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(2003, 7, 2))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto31() {
        return UserDto.builder()
              .id(31)
              .name("Evelyn Brown")
              .email("evelyn.brown@example.com")
              .password("#Evelyn_Brown_Password0")
              .mobile("+40723145631")
              .address("777 Oak St, Mexico City, Mexico")
              .birthday(LocalDate.of(1999, 3, 16))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto32() {
        return UserDto.builder()
              .id(32)
              .name("Henry Davis")
              .email("henry.davis@example.com")
              .password("#Henry_Davis_Password0")
              .mobile("+40787654332")
              .address("999 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(1998, 10, 29))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto33() {
        return UserDto.builder()
              .id(33)
              .name("Sofia Smith")
              .email("sofia.smith@example.com")
              .password("#Sofia_Smith_Password0")
              .mobile("+40754321833")
              .address("111 Oak St, Paris, France")
              .birthday(LocalDate.of(2000, 6, 11))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto34() {
        return UserDto.builder()
              .id(34)
              .name("Jack Wilson")
              .email("jack.wilson@example.com")
              .password("#Jack_Wilson_Password0")
              .mobile("+40789012634")
              .address("333 Elm St, London, UK")
              .birthday(LocalDate.of(1997, 1, 24))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto35() {
        return UserDto.builder()
              .id(35)
              .name("Emily Anderson")
              .email("emily.anderson@example.com")
              .password("#Emily_Anderson_Password0")
              .mobile("+40723145635")
              .address("555 Pine St, Berlin, Germany")
              .birthday(LocalDate.of(1995, 8, 6))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto36() {
        return UserDto.builder()
              .id(36)
              .name("Adrian Thompson")
              .email("adrian.thompson@example.com")
              .password("#Adrian_Thompson_Password0")
              .mobile("+40787654336")
              .address("777 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1996, 3, 20))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto37() {
        return UserDto.builder()
              .id(37)
              .name("Abigail Johnson")
              .email("abigail.johnson@example.com")
              .password("#Abigail_Johnson_Password0")
              .mobile("+40754321837")
              .address("999 Oak St, Athens, Greece")
              .birthday(LocalDate.of(2000, 10, 2))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto38() {
        return UserDto.builder()
              .id(38)
              .name("Michael Davis")
              .email("michael.davis@example.com")
              .password("#Michael_Davis_Password0")
              .mobile("+40789012638")
              .address("111 Oak St, Madrid, Spain")
              .birthday(LocalDate.of(1994, 5, 16))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto39() {
        return UserDto.builder()
              .id(39)
              .name("Mia Wilson")
              .email("mia.wilson@example.com")
              .password("#Mia_Wilson_Password0")
              .mobile("+40723145639")
              .address("333 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1990, 12, 29))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto40() {
        return UserDto.builder()
              .id(40)
              .name("James Lee")
              .email("james.lee@example.com")
              .password("#James_Lee_Password0")
              .mobile("+40787654340")
              .address("555 Pine St, Seoul, South Korea")
              .birthday(LocalDate.of(1991, 8, 11))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto41() {
        return UserDto.builder()
              .id(41)
              .name("Maria Thompson")
              .email("maria.thompson@example.com")
              .password("#Maria_Thompson_Password0")
              .mobile("+40754321841")
              .address("777 Elm St, Beijing, China")
              .birthday(LocalDate.of(1993, 3, 24))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto42() {
        return UserDto.builder()
              .id(42)
              .name("Ethan Smith")
              .email("ethan.smith@example.com")
              .password("#Ethan_Smith_Password0")
              .mobile("+40789012642")
              .address("999 Oak St, Cape Town, South Africa")
              .birthday(LocalDate.of(1989, 11, 6))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto43() {
        return UserDto.builder()
              .id(43)
              .name("Olivia Smith")
              .email("olivia.smith@example.com")
              .password("#Olivia_Smith_Password0")
              .mobile("+40723145643")
              .address("111 Elm St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(1994, 6, 19))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto44() {
        return UserDto.builder()
              .id(44)
              .name("Emily Davis")
              .email("emily.davis@example.com")
              .password("#Emily_Davis_Password0")
              .mobile("+40787654344")
              .address("333 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1998, 1, 1))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto45() {
        return UserDto.builder()
              .id(45)
              .name("Henry Wilson")
              .email("henry.wilson@example.com")
              .password("#Henry_Wilson_Password0")
              .mobile("+40754321845")
              .address("555 Pine St, Mexico City, Mexico")
              .birthday(LocalDate.of(2001, 8, 14))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto46() {
        return UserDto.builder()
              .id(46)
              .name("Scarlett Thompson")
              .email("scarlett.thompson@example.com")
              .password("#Scarlett_Thompson_Password0")
              .mobile("+40789012646")
              .address("777 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(2002, 3, 28))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto47() {
        return UserDto.builder()
              .id(47)
              .name("Jacob Brown")
              .email("jacob.brown@example.com")
              .password("#Jacob_Brown_Password0")
              .mobile("+40723145647")
              .address("999 Pine St, Paris, France")
              .birthday(LocalDate.of(1999, 11, 10))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto48() {
        return UserDto.builder()
              .id(48)
              .name("Ava Smith")
              .email("ava.smith@example.com")
              .password("#Ava_Smith_Password0")
              .mobile("+40787654348")
              .address("111 Pine St, London, UK")
              .birthday(LocalDate.of(1986, 6, 23))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto49() {
        return UserDto.builder()
              .id(49)
              .name("Oliver Johnson")
              .email("oliver.johnson@example.com")
              .password("#Oliver_Johnson_Password0")
              .mobile("+40754321849")
              .address("333 Elm St, Berlin, Germany")
              .birthday(LocalDate.of(1988, 2, 5))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto50() {
        return UserDto.builder()
              .id(50)
              .name("Sophia Wilson")
              .email("sophia.wilson@example.com")
              .password("#Sophia_Wilson_Password0")
              .mobile("+40789012650")
              .address("555 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1994, 9, 19))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto51() {
        return UserDto.builder()
              .id(51)
              .name("William Harris")
              .email("william.harris@example.com")
              .password("#William_Harris_Password0")
              .mobile("+40723145651")
              .address("777 Pine St, Athens, Greece")
              .birthday(LocalDate.of(1996, 4, 3))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto52() {
        return UserDto.builder()
              .id(52)
              .name("Mia Johnson")
              .email("mia.johnson@example.com")
              .password("#Mia_Johnson_Password0")
              .mobile("+40787654352")
              .address("999 Oak St, Madrid, Spain")
              .birthday(LocalDate.of(1998, 11, 16))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto53() {
        return UserDto.builder()
              .id(53)
              .name("Jacob Lee")
              .email("jacob.lee@example.com")
              .password("#Jacob_Lee_Password0")
              .mobile("+40754321853")
              .address("111 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1997, 6, 29))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto54() {
        return UserDto.builder()
              .id(54)
              .name("Charlotte Brown")
              .email("charlotte.brown@example.com")
              .password("#Charlotte_Brown_Password0")
              .mobile("+40789012654")
              .address("333 Pine St, Seoul, South Korea")
              .birthday(LocalDate.of(2000, 2, 12))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto55() {
        return UserDto.builder()
              .id(55)
              .name("Ethan Johnson")
              .email("ethan.johnson@example.com")
              .password("#Ethan_Johnson_Password0")
              .mobile("+40723145655")
              .address("555 Elm St, Beijing, China")
              .birthday(LocalDate.of(1998, 9, 25))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto56() {
        return UserDto.builder()
              .id(56)
              .name("Sophia Davis")
              .email("sophia.davis@example.com")
              .password("#Sophia_Davis_Password0")
              .mobile("+40787654356")
              .address("777 Elm St, Cape Town, South Africa")
              .birthday(LocalDate.of(2001, 7, 9))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto57() {
        return UserDto.builder()
              .id(57)
              .name("Jacob Thompson")
              .email("jacob.thompson@example.com")
              .password("#Jacob_Thompson_Password0")
              .mobile("+40754321857")
              .address("999 Pine St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(1996, 2, 22))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto58() {
        return UserDto.builder()
              .id(58)
              .name("Liam Wilson")
              .email("liam.wilson@example.com")
              .password("#Liam_Wilson_Password0")
              .mobile("+40789012658")
              .address("111 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1995, 10, 7))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto59() {
        return UserDto.builder()
              .id(59)
              .name("Ava Brown")
              .email("ava.brown@example.com")
              .password("#Ava_Brown_Password0")
              .mobile("+40723145659")
              .address("333 Elm St, Mexico City, Mexico")
              .birthday(LocalDate.of(1998, 7, 21))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto60() {
        return UserDto.builder()
              .id(60)
              .name("Scarlett Wilson")
              .email("scarlett.wilson@example.com")
              .password("#Scarlett_Wilson_Password0")
              .mobile("+40787654360")
              .address("555 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(2001, 5, 5))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto61() {
        return UserDto.builder()
              .id(61)
              .name("Noah Martinez")
              .email("noah.martinez@example.com")
              .password("#Noah_Martinez_Password0")
              .mobile("+40754321861")
              .address("777 Pine St, Paris, France")
              .birthday(LocalDate.of(1999, 12, 17))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto62() {
        return UserDto.builder()
              .id(62)
              .name("Mia Anderson")
              .email("mia.anderson@example.com")
              .password("#Mia_Anderson_Password0")
              .mobile("+40789012662")
              .address("999 Oak St, London, UK")
              .birthday(LocalDate.of(2002, 9, 30))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto63() {
        return UserDto.builder()
              .id(63)
              .name("Grace Lee")
              .email("grace.lee@example.com")
              .password("#Grace_Lee_Password0")
              .mobile("+40723145663")
              .address("111 Elm St, Berlin, Germany")
              .birthday(LocalDate.of(1996, 5, 14))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto64() {
        return UserDto.builder()
              .id(64)
              .name("Benjamin Taylor")
              .email("benjamin.taylor@example.com")
              .password("#Benjamin_Taylor_Password0")
              .mobile("+40787654364")
              .address("333 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(2002, 12, 27))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto65() {
        return UserDto.builder()
              .id(65)
              .name("Elijah Roberts")
              .email("elijah.roberts@example.com")
              .password("#Elijah_Roberts_Password0")
              .mobile("+40754321865")
              .address("555 Pine St, Sydney, Australia")
              .birthday(LocalDate.of(2003, 1, 27))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto66() {
        return UserDto.builder()
              .id(66)
              .name("Amelia Walker")
              .email("amelia.walker@example.com")
              .password("#Amelia_Walker_Password0")
              .mobile("+40789012666")
              .address("777 Oak St, Rome, Italy")
              .birthday(LocalDate.of(1992, 8, 12))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto67() {
        return UserDto.builder()
              .id(67)
              .name("Daniel Green")
              .email("daniel.green@example.com")
              .password("#Daniel_Green_Password0")
              .mobile("+40723145667")
              .address("999 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1996, 3, 27))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto68() {
        return UserDto.builder()
              .id(68)
              .name("Liam Hall")
              .email("liam.hall@example.com")
              .password("#Liam_Hall_Password0")
              .mobile("+40787654368")
              .address("111 Oak St, Athens, Greece")
              .birthday(LocalDate.of(1998, 11, 9))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto69() {
        return UserDto.builder()
              .id(69)
              .name("Sophia Young")
              .email("sophia.young@example.com")
              .password("#Sophia_Young_Password0")
              .mobile("+40754321869")
              .address("333 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1995, 6, 23))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .build();
    }

    public static UserDto getMockedUserDto70() {
        return UserDto.builder()
              .id(70)
              .name("Noah Clark")
              .email("noah.clark@example.com")
              .password("#Noah_Clark_Password0")
              .mobile("+40789012670")
              .address("555 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1997, 2, 5))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto71() {
        return UserDto.builder()
              .id(71)
              .name("Olivia Hill")
              .email("olivia.hill@example.com")
              .password("#Olivia_Hill_Password0")
              .mobile("+40723145671")
              .address("777 Oak St, Seoul, South Korea")
              .birthday(LocalDate.of(2001, 9, 20))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }

    public static UserDto getMockedUserDto72() {
        return UserDto.builder()
              .id(72)
              .name("Michaela Allen")
              .email("michaela.allen@example.com")
              .password("#Michaela_Allen_Password0")
              .mobile("+40787654372")
              .address("999 Pine St, Beijing, China")
              .birthday(LocalDate.of(1998, 5, 6))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .build();
    }
}
