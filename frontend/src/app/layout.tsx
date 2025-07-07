import { AuthProvider } from "@/lib/actions/AuthContext";
import { geist } from "@/style/fonts";
import Footer from "@/components/footer";
import "@/style/global.css";
import Navbar from "@/components/navbar";
import { ThemeProvider } from "@/components/theme-provider";

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en" >
      <body className={`${geist.className} antialiased`} >
        {/* <ThemeProvider
              attribute="class"
              defaultTheme="default"
              enableSystem
              disableTransitionOnChange
        > */}
          <AuthProvider>
            <Navbar/>
            {children}
            <Footer/>
          </AuthProvider>
        {/* </ThemeProvider> */}
      </body>
    </html>
  );
}