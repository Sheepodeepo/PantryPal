import NavBar from "@/components/nav-bar"
import { getCurrentUser } from "@/lib/actions/auth-actions"

export default async function NavBarWrapper() {
  const user = await getCurrentUser()
  return <NavBar user={user} />
}
