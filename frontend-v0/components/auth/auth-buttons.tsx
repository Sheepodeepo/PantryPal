import Link from "next/link"
import { Button } from "@/components/ui/button"

export default function AuthButtons() {
  return (
    <div className="flex items-center gap-2">
      <Button variant="outline" asChild>
        <Link href="/login">Login</Link>
      </Button>
      <Button asChild>
        <Link href="/signup">Sign Up</Link>
      </Button>
    </div>
  )
}
