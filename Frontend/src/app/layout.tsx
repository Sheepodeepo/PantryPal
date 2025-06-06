import { geist } from "@/app/ui/fonts";
import "@/app/ui/global.css";
 
export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={`${geist.className} antialiased`} >
        {children}
      </body>
    </html>
  );
}
