"use client";
import React from "react";
import { Center, Heading } from "@chakra-ui/react";
import YouTube from "react-youtube";

const Video = () => {
  const videoId = "AN9OX2ZJ5y0";

  const playerOptions = {
    height: "480",
    width: "800",
    playerVars: {
      autoplay: 1,
    },
  };

  return (
    <>
      <Heading as="h1" size="2xl" textAlign="center" mb="4" p="4">
        Watch our presentation!
      </Heading>
      <Center p="15">
        <YouTube videoId={videoId} opts={playerOptions} />
      </Center>
    </>
  );
};

export default Video;
