import type React from "react"
import type { Metadata } from "next"
import { Inter } from "next/font/google"
import "./globals.css"
import Link from "next/link"
import NavBarWrapper from "@/components/nav-bar-wrapper"

const inter = Inter({ subsets: ["latin"] })

export const metadata: Metadata = {
  title: "PantryPal",
  description: "Your personal cooking companion - discover delicious recipes for every occasion",
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
        <footer className="bg-gray-50 border-t mt-12">
          <div className="container mx-auto px-4 py-8">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
              <div>
                <h3 className="font-bold text-lg mb-4">PantryPal</h3>
                <p className="text-gray-600">Your personal cooking companion for every meal and occasion.</p>
              </div>
              <div>
                <h3 className="font-bold text-lg mb-4">Quick Links</h3>
                <ul className="space-y-2">
                  <li>
                    <Link href="/" className="text-gray-600 hover:text-gray-900">
                      Home
                    </Link>
                  </li>
                  <li>
                    <Link href="/categories" className="text-gray-600 hover:text-gray-900">
                      Categories
                    </Link>
                  </li>
                  <li>
                    <Link href="/favorites" className="text-gray-600 hover:text-gray-900">
                      Favorites
                    </Link>
                  </li>
                  <li>
                    <Link href="/about" className="text-gray-600 hover:text-gray-900">
                      About Us
                    </Link>
                  </li>
                </ul>
              </div>
            </div>
            <div className="border-t mt-8 pt-8 text-center text-gray-600">
              <p>© {new Date().getFullYear()} PantryPal. All rights reserved.</p>
            </div>
          </div>
        </footer>
      </body>
    </html>
  )
}
