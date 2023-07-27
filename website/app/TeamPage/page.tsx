"use client";

import {
  Avatar,
  Box,
  chakra,
  Container,
  Flex,
  Icon,
  SimpleGrid,
  useColorModeValue,
} from "@chakra-ui/react";

const testimonials = [
  {
    name: "Jared Mandap",
    role: "Project manager and Backend developer",
    content:
      "This group has been an absolute pleasure to collaborate with. Throughout our time together, I've gained extensive knowledge about software design patterns, contributed to large-scale system development, embraced agile software development practices, and deepened my understanding of crafting intricate SQL queries.",
    avatar:
      "https://images.unsplash.com/photo-1600259828526-77f8617ceec9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80",
  },
  {
    name: "Arsalan Siddiqui",
    role: "Full stack wizard",
    content:
      "I have honed my skills in designing user-friendly interfaces using Android Studio, creating seamless user experiences. I have also gained proficiency in managing data storage and writing effective tests to ensure software reliability, while employing Git and Agile methodologies for efficient collaboration and project management.",
    avatar:
      "https://images.unsplash.com/photo-1616646187794-d3007d1923a9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
  },
  {
    name: "Noah Wu",
    role: "Backend developer and Requirement gathering",
    content:
      "I've learned diverse skill set that includes adeptly managing data storage, crafting comprehensive tests, proficiently planning projects and sprints, keenly identifying code smells, and actively engaging in productive pair programming sessions for efficient collaboration.",
    avatar:
      "https://images.unsplash.com/photo-1594070319944-7c0cbebb6f58?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1100&q=80",
  },
  {
    name: "Fidelio Ciandy",
    role: "Quality assurance and Backend developer",
    content:
      "As a developer, I've acquired expertise in Android UI design, learning different testing approaches, understanding software architecture, managing project flow with branching strategies, honing project planning skills, and effectively identifying and addressing code smells while implementing clean code practices.",
    avatar:
      "https://images.unsplash.com/photo-1616634375264-2d2e17736a36?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1169&q=80",
  },
  {
    name: "Roba Geleta",
    role: "Arthitecture designer and Quality assurance",
    content:
      "I've acquired valuable skills in architecture design, allowing me to create scalable and maintainable software solutions. Through hands-on experience, I've also become proficient in testing, code smell detection, emulation usage, and database creation, ensuring effective data management strategies.",
    avatar:
      "https://images.unsplash.com/photo-1642912832910-7d403ce697e1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=620&q=80",
  },
];

const backgrounds = [
  `url("data:image/svg+xml, %3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'560\' height=\'185\' viewBox=\'0 0 560 185\' fill=\'none\'%3E%3Cellipse cx=\'102.633\' cy=\'61.0737\' rx=\'102.633\' ry=\'61.0737\' fill=\'%23ED64A6\' /%3E%3Cellipse cx=\'399.573\' cy=\'123.926\' rx=\'102.633\' ry=\'61.0737\' fill=\'%23F56565\' /%3E%3Cellipse cx=\'366.192\' cy=\'73.2292\' rx=\'193.808\' ry=\'73.2292\' fill=\'%2338B2AC\' /%3E%3Cellipse cx=\'222.705\' cy=\'110.585\' rx=\'193.808\' ry=\'73.2292\' fill=\'%23ED8936\' /%3E%3C/svg%3E")`,
  `url("data:image/svg+xml, %3Csvg xmlns='http://www.w3.org/2000/svg' width='560' height='185' viewBox='0 0 560 185' fill='none'%3E%3Cellipse cx='457.367' cy='123.926' rx='102.633' ry='61.0737' transform='rotate(-180 457.367 123.926)' fill='%23ED8936'/%3E%3Cellipse cx='160.427' cy='61.0737' rx='102.633' ry='61.0737' transform='rotate(-180 160.427 61.0737)' fill='%2348BB78'/%3E%3Cellipse cx='193.808' cy='111.771' rx='193.808' ry='73.2292' transform='rotate(-180 193.808 111.771)' fill='%230BC5EA'/%3E%3Cellipse cx='337.295' cy='74.415' rx='193.808' ry='73.2292' transform='rotate(-180 337.295 74.415)' fill='%23ED64A6'/%3E%3C/svg%3E")`,
  `url("data:image/svg+xml, %3Csvg xmlns='http://www.w3.org/2000/svg' width='560' height='185' viewBox='0 0 560 185' fill='none'%3E%3Cellipse cx='102.633' cy='61.0737' rx='102.633' ry='61.0737' fill='%23ED8936'/%3E%3Cellipse cx='399.573' cy='123.926' rx='102.633' ry='61.0737' fill='%2348BB78'/%3E%3Cellipse cx='366.192' cy='73.2292' rx='193.808' ry='73.2292' fill='%230BC5EA'/%3E%3Cellipse cx='222.705' cy='110.585' rx='193.808' ry='73.2292' fill='%23ED64A6'/%3E%3C/svg%3E")`,
  `url("data:image/svg+xml, %3Csvg xmlns='http://www.w3.org/2000/svg' width='560' height='185' viewBox='0 0 560 185' fill='none'%3E%3Cellipse cx='457.367' cy='123.926' rx='102.633' ry='61.0737' transform='rotate(-180 457.367 123.926)' fill='%23ECC94B'/%3E%3Cellipse cx='160.427' cy='61.0737' rx='102.633' ry='61.0737' transform='rotate(-180 160.427 61.0737)' fill='%239F7AEA'/%3E%3Cellipse cx='193.808' cy='111.771' rx='193.808' ry='73.2292' transform='rotate(-180 193.808 111.771)' fill='%234299E1'/%3E%3Cellipse cx='337.295' cy='74.415' rx='193.808' ry='73.2292' transform='rotate(-180 337.295 74.415)' fill='%2348BB78'/%3E%3C/svg%3E")`,
];

interface TestimonialCardProps {
  name: string;
  role: string;
  content: string;
  avatar: string;
  index: number;
}

function TestimonialCard(props: TestimonialCardProps) {
  const { name, role, content, avatar, index } = props;
  return (
    <Flex
      boxShadow={"lg"}
      maxW={"640px"}
      direction={{ base: "column-reverse", md: "row" }}
      width={"full"}
      rounded={"xl"}
      p={10}
      justifyContent={"space-between"}
      position={"relative"}
      bg={useColorModeValue("white", "gray.800")}
      _after={{
        content: '""',
        position: "absolute",
        height: "21px",
        width: "29px",
        left: "35px",
        top: "-10px",
        backgroundSize: "cover",
        backgroundImage: `url("data:image/svg+xml, %3Csvg xmlns='http://www.w3.org/2000/svg' width='29' height='21' viewBox='0 0 29 21' fill='none'%3E%3Cpath d='M6.91391 21C4.56659 21 2.81678 20.2152 1.66446 18.6455C0.55482 17.0758 0 15.2515 0 13.1727C0 11.2636 0.405445 9.43939 1.21634 7.7C2.0699 5.91818 3.15821 4.3697 4.48124 3.05454C5.84695 1.69697 7.31935 0.678787 8.89845 0L13.3157 3.24545C11.5659 3.96667 9.98676 4.94242 8.57837 6.17273C7.21266 7.36061 6.25239 8.63333 5.69757 9.99091L6.01766 10.1818C6.27373 10.0121 6.55114 9.88485 6.84989 9.8C7.19132 9.71515 7.63944 9.67273 8.19426 9.67273C9.34658 9.67273 10.4776 10.097 11.5872 10.9455C12.7395 11.7939 13.3157 13.1091 13.3157 14.8909C13.3157 16.8848 12.6542 18.4121 11.3311 19.4727C10.0508 20.4909 8.57837 21 6.91391 21ZM22.5982 21C20.2509 21 18.5011 20.2152 17.3488 18.6455C16.2391 17.0758 15.6843 15.2515 15.6843 13.1727C15.6843 11.2636 16.0898 9.43939 16.9007 7.7C17.7542 5.91818 18.8425 4.3697 20.1656 3.05454C21.5313 1.69697 23.0037 0.678787 24.5828 0L29 3.24545C27.2502 3.96667 25.6711 4.94242 24.2627 6.17273C22.897 7.36061 21.9367 8.63333 21.3819 9.99091L21.702 10.1818C21.9581 10.0121 22.2355 9.88485 22.5342 9.8C22.8756 9.71515 23.3238 9.67273 23.8786 9.67273C25.0309 9.67273 26.1619 10.097 27.2715 10.9455C28.4238 11.7939 29 13.1091 29 14.8909C29 16.8848 28.3385 18.4121 27.0155 19.4727C25.7351 20.4909 24.2627 21 22.5982 21Z' fill='%239F7AEA'/%3E%3C/svg%3E")`,
      }}
      _before={{
        content: '""',
        position: "absolute",
        zIndex: "-1",
        height: "full",
        maxW: "640px",
        width: "full",
        filter: "blur(40px)",
        transform: "scale(0.98)",
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
        top: 0,
        left: 0,
        backgroundImage: backgrounds[index % 4],
      }}
    >
      <Flex
        direction={"column"}
        textAlign={"left"}
        justifyContent={"space-between"}
      >
        <chakra.p
          fontFamily={"Inter"}
          fontWeight={"medium"}
          fontSize={"15px"}
          pb={4}
        >
          {content}
        </chakra.p>
        <chakra.p fontFamily={"Work Sans"} fontWeight={"bold"} fontSize={14}>
          {name}
          <chakra.span
            fontFamily={"Inter"}
            fontWeight={"medium"}
            color={"gray.500"}
          >
            {" "}
            - {role}
          </chakra.span>
        </chakra.p>
      </Flex>
      <Avatar
        src={avatar}
        height={"80px"}
        width={"80px"}
        alignSelf={"center"}
        m={{ base: "0 0 35px 0", md: "0 0 0 50px" }}
      />
    </Flex>
  );
}

export default function TeamPage() {
  return (
    <Flex
      textAlign={"center"}
      pt={10}
      justifyContent={"center"}
      direction={"column"}
      width={"full"}
      overflow={"hidden"}
    >
      <Box width={{ base: "full", sm: "lg", lg: "xl" }} margin={"auto"}>
        <chakra.h3
          fontFamily={"Work Sans"}
          fontWeight={"bold"}
          fontSize={20}
          textTransform={"uppercase"}
          color={"red.400"}
        >
          Group 13
        </chakra.h3>
        <chakra.h1
          py={5}
          fontSize={48}
          fontFamily={"Work Sans"}
          fontWeight={"bold"}
          color={useColorModeValue("gray.700", "gray.50")}
        >
          Team Honda
        </chakra.h1>
        <chakra.h2
          margin={"auto"}
          width={"70%"}
          fontFamily={"Inter"}
          fontWeight={"medium"}
          color={useColorModeValue("gray.500", "gray.400")}
        >
          Meet the Team!
        </chakra.h2>
      </Box>
      <SimpleGrid
        columns={{ base: 1, xl: 2 }}
        spacing={"20"}
        mt={16}
        mb={16}
        mx={"auto"}
      >
        {testimonials.map((cardInfo, index) => (
          <TestimonialCard key={index} {...cardInfo} index={index} />
        ))}
      </SimpleGrid>
      <Box>
        <Icon viewBox="0 0 45 45" mt={14} boxSize={10} color={"black"}>
          <g>
            <path
              fill={"currentColor"}
              d="M32.6,7.2c-1,4.6-1.4,6.7-2.3,10s-1.4,6.1-2.5,7.6c-0.8,1.1-2.1,1.8-3.5,1.9c-1.3,0.1-2.7,0.1-4,0
            c-1.4-0.1-2.7-0.8-3.5-1.9c-1.1-1.4-1.7-4.4-2.5-7.6S13,11.8,12,7.2l-1.5,0.1c-0.6,0-1.1,0.1-1.6,0.2c0,0,0.6,9.4,0.9,13.4
            c0.3,4.2,0.8,11.2,1.2,16.6c0,0,0.9,0.1,2.3,0.2s2.2,0.1,2.2,0.1c0.6-2.4,1.4-5.6,2.2-7c0.5-0.8,1.4-1.3,2.4-1.3
            c0.7-0.1,1.4-0.1,2.2-0.1l0,0c0.7,0,1.4,0,2.2,0.1c1,0,1.9,0.5,2.4,1.3c0.9,1.4,1.6,4.6,2.2,7c0,0,0.7,0,2.2-0.1s2.3-0.2,2.3-0.2
            c0.5-5.3,1-12.4,1.2-16.6c0.3-4,0.9-13.4,0.9-13.4c-0.5-0.1-1-0.1-1.6-0.2L32.6,7.2z"
            />
            <path
              d="M44.4,12.8c-0.6-6-4.6-7.2-8.1-7.8c-2.3-0.3-4.6-0.5-6.9-0.6c-1.8-0.1-5.9-0.2-7.1-0.2s-5.4,0.1-7.1,0.2
            C12.8,4.4,10.5,4.6,8.2,5c-3.5,0.6-7.5,1.9-8.1,7.8c-0.2,2-0.2,4-0.2,6c0,2.7,0.2,5.4,0.6,8.1c0.2,2.3,0.7,4.5,1.3,6.8
            c0.9,2.6,1.7,3.4,2.6,4.1c1.6,1.1,3.3,1.7,5.2,2c8.4,0.9,16.8,0.9,25.2,0c1.9-0.3,3.6-0.9,5.2-2c1-0.8,1.8-1.5,2.6-4.1
            c0.6-2.2,1.1-4.5,1.3-6.8c0.3-2.7,0.5-5.4,0.6-8.1C44.6,16.8,44.5,14.8,44.4,12.8z M42.2,22.8c-0.1,3.3-0.6,6.5-1.4,9.7
            c-0.3,1.5-1.1,2.8-2.2,3.9c-1.5,1.2-3.3,1.9-5.1,2c-7.5,0.8-15,0.8-22.5,0c-1.9-0.1-3.6-0.8-5.1-2c-1.1-1.1-1.8-2.4-2.2-3.9
            c-0.8-3.2-1.2-6.4-1.4-9.7c-0.2-3.3-0.2-6.7,0.1-10C3,9,4.8,7.2,8.8,6.5c2.2-0.4,4.5-0.6,6.7-0.7c1.9-0.1,5-0.2,6.8-0.2
            s4.9,0,6.8,0.2c2.2,0.1,4.5,0.3,6.7,0.7c4,0.7,5.8,2.6,6.3,6.3C42.3,16.1,42.4,19.5,42.2,22.8L42.2,22.8z"
            />
          </g>
        </Icon>
      </Box>
    </Flex>
  );
}
