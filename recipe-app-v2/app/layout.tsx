import type React from "react"
import type { Metadata } from "next"
import { Inter } from "next/font/google"
import "./globals.css"
import Link from "next/link"
import NavBarWrapper from "@/components/nav-bar-wrapper"
import Footer from "@/components/footer"

const inter = Inter({ subsets: ["latin"] })

export const metadata: Metadata = {
  title: "Recipe App",
  description: "Discover delicious recipes for every occasion",
    generator: 'v0.dev'
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <NavBarWrapper />
        <main>{children}</main>
        <Footer/>
      </body>
    </html>
  )
}
