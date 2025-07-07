import * as React from "react"

import { cn } from "@/lib/utils"

interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
  className?: string;
}

function Card({ className, ...props }: React.ComponentProps<'div'>): React.JSX.Element {
    return (
    <div
      className={cn(
        "rounded-lg border border-gray-200 bg-white text-gray-900 shadow-sm",
        className
      )}
      {...props} />
  )
}

function CardHeader({ className, ...props } : React.ComponentProps<'div'>): React.JSX.Element{
  return (
    <div
      className={cn("flex flex-col space-y-1.5 p-6", className)}
      {...props}
    />
  )
}

function CardTitle({ className, ...props } : React.ComponentProps<'div'>): React.JSX.Element{
  return (
    <div
      className={cn("text-2xl font-semibold leading-none tracking-tight", className)}
      {...props}
    />
  )
}

function CardDescription({ className, ...props } : React.ComponentProps<'div'>) : React.JSX.Element{
  return (
    <div
      className={cn("text-sm text-muted-foreground", className)}
      {...props}
  />
  )
}

function CardContent({ className, ...props } : React.ComponentProps<'div'> ): React.JSX.Element {
  return (
    <div 
      className={cn("p-6 pt-0", className)} 
      {...props} 
    />
  )
}

function CardFooter({ className, ...props } : React.ComponentProps<'div'> ): React.JSX.Element {
  return (
    <div
      className={cn("flex items-center p-6 pt-0", className)}
      {...props}
  />
  )
}

export { Card, CardHeader, CardFooter, CardTitle, CardDescription, CardContent }
