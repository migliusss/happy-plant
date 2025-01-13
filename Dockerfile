###################################################
################  BACKEND STAGES  #################
###################################################

#####################################################
# Stage: backend
#
# This stage provides a Java Development Kit (JDK)
# environment for building the application.
# Install buld tools, copy source code and build
# commands for compile.
#####################################################
FROM eclipse-temurin:21.0.2_13-jdk-jammy AS backend
WORKDIR /app
COPY /backend/.mvn/ .mvn
COPY /backend/mvnw /backend/pom.xml ./
RUN ./mvnw dependency:go-offline
COPY /backend/src ./src
CMD ["./mvnw", "spring-boot:run"]

#####################################################
################## FRONTEND STAGES ##################
#####################################################

#####################################################
# Docker Image for Next.js was created using this example from next.js
# https://github.com/vercel/next.js/tree/canary/examples/with-docker
#####################################################

#####################################################
# Stage: frontend
#
# This frontend stage builds environment and
# ensures all other stages are using the same base image.
#####################################################
FROM node:22.13-alpine AS frontend

#####################################################
# Stage: deps
#
# This stage is used to install dependencies.
# Having dependencies here ensures they are only
# installed when needed.
#####################################################
FROM frontend AS frontend-base
RUN apk add --no-cache libc6-compat
WORKDIR /app
COPY /frontend/package.json /frontend/pnpm-lock.yaml* ./
RUN corepack enable pnpm && pnpm i --frozen-lockfile

#####################################################
# Stage: builder
#
# This stage is used to build the client application.
#####################################################
FROM frontend AS builder
WORKDIR /app
COPY --from=frontend-base /app/node_modules ./node_modules
COPY /frontend .
RUN corepack enable pnpm && pnpm run build

#####################################################
# Stage: runner (production image)
#
# This stage is used to run the client application.
#####################################################
FROM frontend AS runner
WORKDIR /app
ENV NODE_ENV=development
RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs
COPY --from=builder /app/public ./public
COPY --from=builder --chown=nextjs:nodejs /app/.next/standalone ./
COPY --from=builder --chown=nextjs:nodejs /app/.next/static ./.next/static
USER nextjs
EXPOSE 3000
ENV PORT=3000
ENV HOSTNAME="0.0.0.0"
CMD ["node", "server.js"]

