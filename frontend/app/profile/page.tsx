import { redirect } from "next/navigation"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { getCurrentUser, logout } from "@/lib/actions/auth-actions"

export default async function ProfilePage() {
  const user = await getCurrentUser()

  if (!user) {
    redirect("/login")
  }

  return (
    <div className="container mx-auto px-4 py-12">
      <div className="max-w-3xl mx-auto">
        <h1 className="text-3xl font-bold mb-8">Your Profile</h1>

        <Card className="mb-8">
          <CardHeader>
            <CardTitle>Account Information</CardTitle>
            <CardDescription>Manage your PantryPal account information</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div>
                <h3 className="text-sm font-medium text-gray-500">Name</h3>
                <p className="text-lg">{user.name}</p>
              </div>
              <div>
                <h3 className="text-sm font-medium text-gray-500">Email</h3>
                <p className="text-lg">{user.email}</p>
              </div>
              <div className="pt-4">
                <Button variant="outline">Edit Profile</Button>
              </div>
            </div>
          </CardContent>
        </Card>

        <Card className="mb-8">
          <CardHeader>
            <CardTitle>Your Activity</CardTitle>
            <CardDescription>See your recent activity on PantryPal</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div className="flex justify-between items-center">
                <div>
                  <h3 className="font-medium">Saved Recipes</h3>
                  <p className="text-sm text-gray-500">Recipes you've saved to your favorites</p>
                </div>
                <Button variant="outline" size="sm">
                  View All
                </Button>
              </div>
              <div className="flex justify-between items-center">
                <div>
                  <h3 className="font-medium">Your Recipes</h3>
                  <p className="text-sm text-gray-500">Recipes you've created</p>
                </div>
                <Button variant="outline" size="sm">
                  View All
                </Button>
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Account Settings</CardTitle>
            <CardDescription>Manage your account settings</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div>
                <Button variant="outline">Change Password</Button>
              </div>
              <div>
                <form action={logout}>
                  <Button variant="destructive" type="submit">
                    Logout
                  </Button>
                </form>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
