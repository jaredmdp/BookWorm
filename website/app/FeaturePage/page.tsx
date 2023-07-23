"use client";
import {
  Container,
  SimpleGrid,
  Image,
  Flex,
  Heading,
  Text,
  Stack,
  StackDivider,
  Icon,
  useColorModeValue,
  Spacer,
} from "@chakra-ui/react";
import {
  IoAnalyticsSharp,
  IoLogoBitcoin,
  IoSearchSharp,
} from "react-icons/io5";
import { ReactElement } from "react";

interface FeatureProps {
  text: string;
  iconBg: string;
  icon?: ReactElement;
}

const Feature = ({ text, icon, iconBg }: FeatureProps) => {
  return (
    <Stack direction={"row"} align={"center"}>
      <Flex
        w={8}
        h={8}
        align={"center"}
        justify={"center"}
        rounded={"full"}
        bg={iconBg}
      >
        {icon}
      </Flex>
      <Text fontWeight={600}>{text}</Text>
    </Stack>
  );
};

export default function FeaturePage() {
  return (
    // SearchBooks feature
    <Container maxW={"5xl"} py={12}>
      <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
        <Flex>
          <Image
            rounded={"md"}
            src={"/Images/searchBooks.png"}
            alt={"feature image"}
            objectFit={"cover"}
            width={500}
            height={500}
          />
        </Flex>
        <Stack spacing={4}>
          <Text
            textTransform={"uppercase"}
            color={"blue.400"}
            fontWeight={600}
            fontSize={"sm"}
            bg={useColorModeValue("blue.50", "blue.900")}
            p={2}
            alignSelf={"flex-start"}
            rounded={"md"}
          >
            Feature
          </Text>
          <Heading fontSize={"3xl"}>Search for books</Heading>
          <Text color={"gray.500"} fontSize={"md"}>
            With our Search Bar, you can effortlessly discover users, authors,
            and books that match your interests. Whether you're looking for your
            favorite author's latest books, a specific book title, an intriguing
            genre, or an ISBN number, our Search Bar has got you covered!
          </Text>
          <Text color={"gray.500"} fontSize={"md"}>
            Simply type in your input, push enter, and streamline your book
            browsing experience.
          </Text>
          <Stack
            spacing={4}
            divider={
              <StackDivider
                borderColor={useColorModeValue("gray.100", "gray.700")}
              />
            }
          >
            <Feature
              icon={
                <Icon as={IoAnalyticsSharp} color={"yellow.500"} w={5} h={5} />
              }
              iconBg={useColorModeValue("yellow.100", "yellow.900")}
              text={"Optional marketing quote"}
            />
            <Feature
              icon={<Icon as={IoLogoBitcoin} color={"green.500"} w={5} h={5} />}
              iconBg={useColorModeValue("green.100", "green.900")}
              text={"Optional marketing quote"}
            />
          </Stack>
        </Stack>
      </SimpleGrid>

      <Spacer height="70px" />

      {/* AddBooksFeature */}
      <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
        <Stack spacing={4}>
          <Text
            textTransform={"uppercase"}
            color={"blue.400"}
            fontWeight={600}
            fontSize={"sm"}
            bg={useColorModeValue("blue.50", "blue.900")}
            p={2}
            alignSelf={"flex-start"}
            rounded={"md"}
          >
            Feature
          </Text>
          <Heading fontSize={"3xl"}>Add Books</Heading>
          <Text color={"gray.500"} fontSize={"md"}>
            As an author, you have the privilege of adding your own books to our
            system.
          </Text>
          <Text color={"gray.500"} fontSize={"md"}>
            Simply fill in the book details, including the title, ISBN, genre,
            and description. Don't forget to provide an eye-catching image for
            your book.
          </Text>
          <Text color={"gray.500"} fontSize={"md"}>
            Once you submit the information, your custom book will be displayed
            among our collection for readers to explore.
          </Text>
          <Stack
            spacing={4}
            divider={
              <StackDivider
                borderColor={useColorModeValue("gray.100", "gray.700")}
              />
            }
          >
            <Feature
              icon={
                <Icon as={IoAnalyticsSharp} color={"yellow.500"} w={5} h={5} />
              }
              iconBg={useColorModeValue("yellow.100", "yellow.900")}
              text={"Optional marketing quote"}
            />
            <Feature
              icon={<Icon as={IoLogoBitcoin} color={"green.500"} w={5} h={5} />}
              iconBg={useColorModeValue("green.100", "green.900")}
              text={"Optional marketing quote"}
            />
          </Stack>
        </Stack>
        <Flex>
          <Image
            rounded={"md"}
            src={"/Images/addBooks.png"}
            alt={"feature image"}
            objectFit={"cover"}
            width={500}
            height={500}
          />
        </Flex>
      </SimpleGrid>
    </Container>
  );
}
