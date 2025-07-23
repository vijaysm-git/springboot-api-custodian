import SidebarWithHeader from "./components/shared/SideBar.jsx";
import { Box, Heading, Text, VStack, Center } from "@chakra-ui/react";

const Home = () => {
    return (
        <SidebarWithHeader>
            <Center height="80vh" width="100%"> {/* Center content vertically and horizontally */}
                <VStack spacing={6} align="center" px={{ base: 4, md: 8 }}> {/* Add padding for smaller screens */}
                    {/* Main Heading: Custodian */}
                    <Heading
                        as="h1"
                        fontSize={{ base: "5xl", md: "7xl", lg: "8xl" }}
                        fontWeight="extrabold"
                        textAlign="center"
                        bgGradient="linear(to-r, purple.400, pink.400)"
                        bgClip="text"
                        letterSpacing="tight"
                        fontFamily="'Montserrat', sans-serif" // Still suggesting nice fonts
                    >
                        Custodian
                    </Heading>

                    {/* Tagline/Description */}
                    <Text
                        fontSize={{ base: "lg", md: "xl", lg: "2xl" }}
                        color="gray.600"
                        textAlign="center"
                        maxWidth="800px"
                        fontFamily="'Roboto', sans-serif" // Still suggesting nice fonts
                    >
                        Your trusted guardian for digital assets.
                        Secure, intuitive, and effortlessly managed.
                    </Text>

                    {/* Subtle Call to Action or Decorative Element */}
                    <Text
                        fontSize="md"
                        color="gray.400"
                        mt={8}
                        fontFamily="'Inconsolata', monospace" // Still suggesting nice fonts
                    >
                        Protecting what matters most.
                    </Text>
                </VStack>
            </Center>
        </SidebarWithHeader>
    );
};

export default Home;