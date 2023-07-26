"use client";

import { Inter } from "next/font/google";
import { ChakraProvider, ScaleFade } from "@chakra-ui/react";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";

const inter = Inter({ subsets: ["latin"] });
export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <ChakraProvider>
          <Navbar />
          <ScaleFade initialScale={0.1} in>
            {children}
          </ScaleFade>
          <Footer />
        </ChakraProvider>
      </body>
    </html>
  );
}
