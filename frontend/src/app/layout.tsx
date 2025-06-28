import { AuthProvider } from "@/app/context/AuthContext";
import { geist } from "@/app/ui/fonts";
import "@/app/ui/global.css";

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body className={`${geist.className} antialiased`} >
        <AuthProvider>
          {children}
        </AuthProvider>
      </body>
    </html>
  );
}