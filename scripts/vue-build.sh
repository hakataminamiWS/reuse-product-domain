#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# vue build
cd "$SCRIPT_DIR/../frontend"
npm run build

# copy to backend
STATIC_DIR="$SCRIPT_DIR/../backend/src/main/resources/static"
rm -rf "${STATIC_DIR:?}/"*
cp -r dist/. "$STATIC_DIR"/

echo "complete vue build"