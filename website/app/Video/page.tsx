"use client";
import React from "react";
import {
  Box,
  Center,
  Heading,
  AspectRatio,
  useBreakpointValue,
  Spacer,
} from "@chakra-ui/react";

const Video = () => {
  const videoId = "AN9OX2ZJ5y0";

  const aspectRatio = useBreakpointValue({ base: 5 / 4, md: 16 / 9 });

  return (
    <>
      <Heading as="h1" size="2xl" textAlign="center" my="4" p="4">
        Watch our presentation!
      </Heading>
      <Center>
        <Box
          width="100%"
          maxW={{ base: "100%", md: "800px" }}
          boxShadow="xl"
          borderRadius="md"
          overflow="hidden"
          position="relative"
        >
          <AspectRatio ratio={aspectRatio}>
            <Box
              as="iframe"
              title="YouTube video player"
              src={`https://www.youtube.com/embed/${videoId}`}
              frameBorder="0"
              allowFullScreen
              position="absolute"
              top="0"
              left="0"
              width="100%"
              height="100%"
              objectFit="cover"
            />
          </AspectRatio>
        </Box>
      </Center>
      <Spacer height="70px" />
    </>
  );
};

export default Video;
