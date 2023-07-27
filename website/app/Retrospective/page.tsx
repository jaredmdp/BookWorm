"use client";
import {
  Box,
  Heading,
  Stack,
  Text,
  AspectRatio,
  Image,
} from "@chakra-ui/react";
import React from "react";

const RetrospectivePage = () => {
  const retrospectiveData = [
    {
      question: "What went right in the development process?",
      answer: [
        "During iteartion 3, one area we aimed to enhance was the consistency of error handling throughout our project. We identified that different parts of the codebase handled errors differently, leading to clunky and unpredictable behavior.",
        "To address this, we held a meeting to establish a unified approach to error handling. As a result, we now consistently throw and catch errors, ensuring that they are handled appropriately and consistently across the project. This has reduced technical debt and improved the clarity of error messages during debugging.",
      ],
    },
    {
      question: "What went wrong in the development process?",
      answer: [
        "A part of our project that has bee unsuccessful is our code review process and code quality came under scrutiny when we received numerous code smell reports from the markers. Despite our efforts to perform a code smell pass, it became evident that we needed to take a more comprehensive approach to address these issues.",
        "To tackle the challenge of improving code review and code quality, we decided to adopt a collaborative and team-driven approach. Instead of relying on individual code smell passes, we recognized the value of having multiple perspectives on the codebase. Therefore, in this iteration, we decided to conduct more thorough smell tests as a team. We actively involved all team members to provide their insights and observations during the code review process.",
      ],
    },
    {
      question: "Improvement after iteration 2 retrospective",
      answer: [
        "An aspect we identified that needs improvement is the planning phase of our development process. In previous iterations, we encountered situations where code required multiple adjustments and refactoring due to insufficient planning. These instances of technical debt resulted in additional developer time spent on refactoring and re-adjusting code.",
        "To address this challenge, we have placed greater emphasis on the planning/design phase of our development cycle. We now take the time to thoroughly discuss all aspects of a feature before implementation. This includes evaluating design choices, considering potential challenges, and anticipating future requirements preventing any Open Closed Principle violations.",
      ],
    },
    {
      question:
        "What would you do differently, if you had the chance to start over?",
      answer: [
        "As we began working on the features envisioned for our application, we came to realize that the scope was larger than we initially anticipated. To fully implement the features that foster a book community, it requires a significant number of additional functionalities to do justice to the vision.",
        "Moreover, we would of liked to have used a different database that supports a database manager instead of relying on a script file. Unfortunately, using the script file resulted in a heavy load when inserting book images.",
      ],
    },
    {
      question: "What took the most time? The least? Any surprises?",
      answer: [
        "In terms of developmental speeds, the database took a significant amount of time to get working. The script file was troublesome and documentation of HyperSQL was difficult to navigate.",
        "The recommended books was surprisingly easy to implement as our algorithm only incorporated the user's favorite books and genres. If we were to implement more features in iterations beyond, this task would take a significant more time as we gain more metrics to incorporate with the recommendation system.",
        "A big surprise for us in iteration 3 was a bug when implementing the comments feature. During our systems testing, we had a general persistence error that would randomly throw that was difficult to replicate. This was eventually fixed by initiating our comments persistence in our homeview.",
      ],
    },
    {
      question:
        "Are you using any specific techniques covered in the course (TDD, pair programming, scrums, etc)?",
      answer: [
        "The techniques utilized by our group during this course were pair programming and prototyping for UI design. When we encountered challenging problems, we adopted a pair programming approach, where two team members would screen share the code base and collaborate in debugging together.",
        "Additionally, we conducted multiple architectural design meetings to ensure that our project's overall structure and design were well thought out and aligned with our project's vision.",
      ],
    },
    {
      question:
        "What did you learn about team or large project development? What will you start doing, keep doing, or stop doing next time?",
      answer: [
        "The most valuable technique we learned during the development of large-scale projects is the Agile methodology. The ability to iterate and continuously refine and ship working features throughout the software development cycle proved highly valuable. Agile had a significant impact on our team's efficiency during the iterations, allowing us to adapt and respond to changes effectively.",
        "Another valuable skill we acquired is version control. Learning version control practices has been crucial, and we recognize its importance and will be using it in our future careers. Having a clear understanding of version control systems helps in maintaining code integrity and collaboration within development teams.",
        "In terms of improvements, we identified the need to stop coding without proper architecture and implementation discussions. We observed that coding without a clear design often leads to technical debt and necessitates large-scale refactoring. Moving forward, we intend to incorporate proper design and take it seriously in the software development lifecycle.",
      ],
    },
  ];

  const imageData = {
    src: "/Images/velocity.png",
    alt: "Feature image",
    width: 600,
    height: 600,
  };

  return (
    <Box maxW="800px" mx="auto" mt="4" p="4">
      <Heading as="h1" size="xl" textAlign="center" mb="4">
        Retrospective
      </Heading>
      <Stack spacing="4">
        {retrospectiveData.map(({ question, answer }, index) => {
          return (
            <Box
              key={index}
              borderWidth="1px"
              borderRadius="lg"
              boxShadow="md"
              p="4"
            >
              <Heading as="h3" size="md" mb="2">
                {question}
              </Heading>
              {answer.map((item, idx) => (
                <Text key={idx} mb="2">
                  {item}
                </Text>
              ))}
            </Box>
          );
        })}
        <Box borderWidth="1px" borderRadius="lg" boxShadow="md" p="4">
          <Heading as="h3" size="md" mb="3">
            Our velocity chart for iteration 1 and 2
          </Heading>
          <Text>
            This is our velocity chart of the first two iterations. We have
            noticed that our estimated work was higher than the completed work
            in the first Iteration. We attribute this shallow understanding of
            how much work was required for the features we didn't finish in
            iteration 1. However, we have made notable improvements in our time
            estimations compared to the first iteration having finished all
            committed work in iteration 2 without having too little work or
            overworking. We will continue to monitor and refine our velocity
            estimates to ensure continued accurate planning and better developer
            task delegations.
          </Text>
          <AspectRatio ratio={1.5}>
            <Box borderRadius="md" overflow="hidden">
              <Image
                src={imageData.src}
                alt={imageData.alt}
                objectFit="contain"
                width={imageData.width}
                height={imageData.height}
              />
            </Box>
          </AspectRatio>
        </Box>
      </Stack>
    </Box>
  );
};

export default RetrospectivePage;
